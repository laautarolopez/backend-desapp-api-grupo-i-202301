package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.TransactionPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TradeService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TransactionService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class TransactionServiceImp(
    private val transactionPersistence: TransactionPersistence,
    private val tradeService: TradeService
    ) : TransactionService {

    override fun create(transaction: Transaction): Transaction {
        recoverTrade(transaction)
        validateShippingAddress(transaction)
        validateAction(transaction)
        return transactionPersistence.save(transaction)
    }

    private fun recoverTrade(transaction: Transaction) {
        transaction.trade = tradeService.getTrade(transaction.trade!!.id!!)
    }

    override fun getTransaction(idTrade: Long): Transaction {
        val transaction = transactionPersistence.findByIdOrNull(idTrade)
        if (transaction == null) {
            throw RuntimeException("The id does not exist.")
        }
        return transaction
    }

    override fun recoverAll(): List<Transaction> {
        return transactionPersistence.findAll()
    }

    override fun clear() {
        transactionPersistence.deleteAll()
    }

    private fun validateShippingAddress(transaction: Transaction) {
        if(transaction.trade!!.operation.toString() == "SALE") {
           if(transaction.shippingAddress!!.length != 22) {
               throw RuntimeException("The shipping address must contain a CVU with 22 digits.")
           }
        } else if(transaction.trade!!.operation.toString() == "BUY") {
            if(transaction.shippingAddress!!.length != 8) {
                throw RuntimeException("The shipping address must contain a walletAddress with 8 digits.")
            }
        }
    }

    private fun validateAction(transaction: Transaction) {
        if(transaction.action.toString() == "MAKE") {
            if(transaction.trade!!.operation.toString() != "BUY") {
                throw RuntimeException(".") // TODO: agregar mensaje de validacion
            }
        } else if(transaction.action.toString() == "CONFIRM") {
            if(transaction.trade!!.operation.toString() != "SALE") {
                throw RuntimeException(".") // TODO: agregar mensaje de validacion
            }
        }
    }
}