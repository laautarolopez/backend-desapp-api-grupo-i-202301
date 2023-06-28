package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.TransactionException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.TransactionPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TradeService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime

@Service
@Validated
@Transactional
class TransactionServiceImp(
    private val transactionPersistence: TransactionPersistence,
    @Autowired
    private val tradeService: TradeService,
    @Autowired
    private val cryptoService: CryptoService,
    @Autowired
    private val userService: UserService
) : TransactionService {

    override fun create(transaction: Transaction): Transaction {
        recoverTrade(transaction)
        val userRequested = recoverUserRequested(transaction)
        updateBuyerSeller(transaction, userRequested)
        return transactionPersistence.save(transaction)
    }

    override fun update(transaction: Transaction): Transaction {
        this.getTransaction(transaction.id)
        return transactionPersistence.save(transaction)
    }

    override fun transfer(transaction: Transaction): Transaction {
        val idUserRequested = transaction.idUserRequested
        val transaction = this.getTransaction(transaction.id)
        transaction.idUserRequested = idUserRequested
        validateTransfer(transaction)
        transaction.status = TransactionStatus.TRANSFERRED
        return update(transaction)
    }

    private fun validateTransfer(transaction: Transaction) {
        validateStatus(transaction, TransactionStatus.CREATED)
        recoverTrade(transaction)
        recoverUserRequested(transaction)
        validateUserRequestedBuyer(transaction)
    }

    private fun validateStatus(transaction: Transaction, status: TransactionStatus) {
        if(transaction.status != status) {
            throw TransactionException("transaction.status", "The status must be ${status}")
        }
    }

    private fun validateUserRequestedBuyer(transaction: Transaction) {
        if(transaction.idUserRequested != transaction.buyer!!.id) {
            throw TransactionException("transaction.buyer", "The user request is not the buyer")
        }
    }

    override fun confirm(transaction: Transaction): Transaction {
        val idUserRequested = transaction.idUserRequested
        val transaction = this.getTransaction(transaction.id)
        transaction.idUserRequested = idUserRequested
        validateConfirm(transaction)

        if(isPriceVariationViolation(transaction)) {
            transaction.status = TransactionStatus.CANCELED
            cancelTrade(transaction.trade!!)
            return update(transaction)
        } else {
            transaction.status = TransactionStatus.CONFIRMED
            cancelTrade(transaction.trade!!)

            addOperationToUsers(transaction)
            addPointsToUsers(transaction)

            transaction.date = LocalDateTime.now()

            return update(transaction)
        }
    }

    private fun validateConfirm(transaction: Transaction) {
        validateStatus(transaction, TransactionStatus.TRANSFERRED)
        recoverTrade(transaction)
        recoverUserRequested(transaction)
        validateUserRequestedSeller(transaction)
    }

    private fun validateUserRequestedSeller(transaction: Transaction) {
        if(transaction.idUserRequested != transaction.seller!!.id) {
            throw TransactionException("transaction.seller", "The user request is not the seller")
        }
    }

    private fun cancelTrade(trade: Trade) {
        val cancelTrade = trade
        cancelTrade.isActive = false
        tradeService.update(trade)
    }

    private fun addOperationToUsers(transaction: Transaction) {
        val buyer = transaction.buyer
        val seller = transaction.seller

        buyer!!.addOperation()
        seller!!.addOperation()
    }

    private fun addPointsToUsers(transaction: Transaction) {
        val buyer = transaction.buyer
        val seller = transaction.seller
        val time = transaction.trade!!.creationDate
        val actualTime = LocalDateTime.now()

        if(actualTime.isBefore(time!!.plusMinutes(30))) {
            buyer!!.addPoints(10)
            seller!!.addPoints(10)
        } else {
            buyer!!.addPoints(5)
            seller!!.addPoints(5)
        }

        userService.update(buyer)
        userService.update(seller)
    }

    override fun cancel(transaction: Transaction): Transaction {
        val idUserRequested = transaction.idUserRequested
        val transaction = this.getTransaction(transaction.id)
        transaction.idUserRequested = idUserRequested
        validateCancel(transaction)
        transaction.status = TransactionStatus.CANCELED
        subtractPoints(recoverUserRequested(transaction))
        return update(transaction)
    }

    private fun validateCancel(transaction: Transaction) {
        validateStatusCancel(transaction)
        recoverTrade(transaction)
        recoverUserRequested(transaction)
        validateUserRequestedBuyerOrSeller(transaction)
    }

    private fun validateStatusCancel(transaction: Transaction) {
        if(transaction.status != TransactionStatus.TRANSFERRED && transaction.status != TransactionStatus.CREATED) {
            throw TransactionException("transaction.status", "The status must be TRANSFERRED or CREATED")
        }
    }

    private fun validateUserRequestedBuyerOrSeller(transaction: Transaction) {
        if(transaction.idUserRequested != transaction.buyer!!.id && transaction.idUserRequested != transaction.seller!!.id) {
            throw TransactionException("transaction.buyer/seller", "The user request is not the buyer/seller")
        }
    }

    private fun subtractPoints(userRequested: User) {
        val user = userRequested
        user.subtractPoints(20)
        userService.update(user)
    }

    private fun recoverTrade(transaction: Transaction) {
        transaction.trade = tradeService.getTrade(transaction.trade!!.id)
        if(!transaction.trade!!.isActive!!) {
            throw TransactionException("trade.isActive", "The trade is not active.")
        }
    }

    private fun recoverUserRequested(transaction: Transaction): User {
        return userService.getUser(transaction.idUserRequested)
    }

    private fun updateBuyerSeller(transaction: Transaction, userRequested: User) {
        if(userRequested == transaction.trade!!.user) {
            throw TransactionException("user requested", "The transaction cannot be created by this user")
        }

        if (isOperationType(transaction, OperationType.BUY)) {
            transaction.buyer = transaction.trade!!.user
            transaction.seller = userRequested
        } else {
            transaction.buyer = userRequested
            transaction.seller = transaction.trade!!.user
        }
    }

    override fun getTransaction(idTransaction: Long?): Transaction {
        validateId(idTransaction)
        try {
            return transactionPersistence.findByIdOrNull(idTransaction!!)!!
        } catch(e: RuntimeException) {
            throw TransactionException("transaction", "The transaction does not exist.")
        }
    }

    private fun validateId(idTransaction: Long?) {
        if(idTransaction == null) {
            throw TransactionException("transaction", "The transaction does not exist.")
        }
    }

    override fun recoverAll(): List<Transaction> {
        return transactionPersistence.findAll().toList()
    }

    override fun recoverConfirmedTransactionsFromUserBetweenTwoDates(idUser: Long, firstDate: LocalDateTime, lastDate: LocalDateTime): List<Transaction> {
        return transactionPersistence.getConfirmedTransactionsFromUserBetweenTwoDates(idUser, firstDate, lastDate, 2)
    }

    override fun clear() {
        transactionPersistence.deleteAll()
    }

    private fun isOperationType(transaction: Transaction, operation: OperationType): Boolean {
        return transaction.trade!!.operation == operation
    }

    private fun isPriceVariationViolation(transaction: Transaction): Boolean {
        val crypto = transaction.trade!!.crypto
        val tradePrice = transaction.trade!!.cryptoPrice
        val actualPrice = cryptoService.getPrice(crypto!!.name.toString()).price
        val maxVariation = tradePrice!! * 0.05

        if(actualPrice < tradePrice) {
            val variation = tradePrice - actualPrice
            return variation > maxVariation
        } else {
            val variation = actualPrice - tradePrice
            return variation > maxVariation
        }
    }
}