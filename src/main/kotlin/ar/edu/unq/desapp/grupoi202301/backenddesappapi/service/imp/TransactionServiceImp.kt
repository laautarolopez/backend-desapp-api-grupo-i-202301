package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.TradePersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.TransactionPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TradeService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class TransactionServiceImp(
    private val transactionPersistence: TransactionPersistence
    ) : TransactionService {

    override fun create(transaction: Transaction): Transaction {
        validateShippingAddress(transaction)
        //validateAction(transaction)
        return transactionPersistence.save(transaction)
    }

    override fun clear() {
        transactionPersistence.deleteAll()
    }

    fun validateShippingAddress(transaction: Transaction) {
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

//    fun validateAction(transaction: Transaction) {
//
//    }
}