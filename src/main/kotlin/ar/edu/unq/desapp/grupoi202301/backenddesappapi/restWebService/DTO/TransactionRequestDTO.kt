package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction

class TransactionRequestDTO(
    var idTransaction: Long?,
    var idUserRequested: Long?
) {

    fun toModel(): Transaction {
        val transaction = Transaction()
        transaction.id = this.idTransaction
        transaction.idUserRequested = this.idUserRequested
        return transaction
    }
}