package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.ActionTransaction
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.OperationType
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.TransactionPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TradeService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TransactionService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class TransactionServiceImp(
    private val transactionPersistence: TransactionPersistence,
    @Autowired
    private val tradeService: TradeService,
    @Autowired
    private val cryptoService: CryptoService
    ) : TransactionService {

    override fun create(transaction: Transaction): Transaction {
        recoverTrade(transaction)
        validateShippingAddress(transaction)
        validateAction(transaction)
        processTransaction(transaction)
        return transactionPersistence.save(transaction)
    }

    private fun recoverTrade(transaction: Transaction) {
        transaction.trade = tradeService.getTrade(transaction.trade!!.id!!)
    }

    override fun getTransaction(idTransaction: Long): Transaction {
        try {
            return transactionPersistence.getReferenceById(idTransaction)
        } catch(e: RuntimeException) {
            throw RuntimeException("The transaction does not exist.")
        }
    }

    override fun recoverAll(): List<Transaction> {
        return transactionPersistence.findAll()
    }

    override fun clear() {
        transactionPersistence.deleteAll()
    }

    private fun validateShippingAddress(transaction: Transaction) {
        if(isOperationType(transaction, OperationType.SALE)) {
            transaction.shippingAddress = transaction.trade!!.user!!.cvuMercadoPago
        } else if(isOperationType(transaction, OperationType.BUY)) {
            transaction.shippingAddress = transaction.trade!!.user!!.walletAddress
        }
    }

    private fun isOperationType(transaction: Transaction, operation: OperationType): Boolean {
        return transaction.trade!!.operation == operation
    }

    private fun validateAction(transaction: Transaction) {
        // TODO: validar si el usuario de la transaccion es comprador o vendedor(no el user del trade)
        if(isActionTransaction(transaction, ActionTransaction.CONFIRM) && isOperationType(transaction, OperationType.BUY)) {
                throw RuntimeException("The action must be 'MAKE' or 'CANCEL' for buy operation.")
        } else if(isActionTransaction(transaction, ActionTransaction.MAKE) && isOperationType(transaction, OperationType.SALE)) {
            throw RuntimeException("The action must be 'CONFIRM' or 'CANCEL' for sale operation.")
        }
    }

    private fun isActionTransaction(transaction: Transaction, action: ActionTransaction): Boolean {
        return transaction.action == action
    }

    private fun processTransaction(transaction: Transaction) {
        if(isSuccessful(transaction)) {

        } else {

        }
    }

    private fun isSuccessful(transaction: Transaction): Boolean {
        return !isActionTransaction(transaction, ActionTransaction.CANCEL) && !isPriceDifferent(transaction)
    }

    private fun isPriceDifferent(transaction: Transaction): Boolean {
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