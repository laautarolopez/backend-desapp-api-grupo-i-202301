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
        return transactionPersistence.save(transaction)
    }
}