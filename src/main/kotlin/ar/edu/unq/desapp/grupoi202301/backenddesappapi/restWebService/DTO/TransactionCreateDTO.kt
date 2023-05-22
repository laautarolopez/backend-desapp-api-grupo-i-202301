package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.ActionTransaction
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.ActionEmptyException

class TransactionCreateDTO(
    var idTrade: Long?,
    var amountOperation: Double?,
    var shippingAddress: String?,
    var action: String?,
) {

    fun toModel(): Transaction {
        val transaction = Transaction()
        val trade = Trade()
        trade.id = this.idTrade
        transaction.trade = trade
        transaction.amountOperation = this.amountOperation
        // TODO: agregar amount con llamado a la api?
        transaction.shippingAddress = this.shippingAddress
        transaction.action = this.verifyAction(this.action)
        return transaction
    }

    companion object {
        fun fromModel(transaction: Transaction) =
            TransactionCreateDTO(
                idTrade = transaction.trade!!.id,
                amountOperation = transaction.amountOperation,
                shippingAddress = transaction.shippingAddress,
                action = transaction.action.toString()
            )
    }

    private fun verifyAction(action: String?): ActionTransaction {
        var newAction: ActionTransaction
        when(action) {
            "MAKE" -> newAction = ActionTransaction.MAKE
            "CONFIRM" -> newAction = ActionTransaction.CONFIRM
            "CANCEL" -> newAction = ActionTransaction.CANCEL
            else -> {
                throw ActionEmptyException()
            }
        }
        return newAction
    }
}