package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.ActionTransaction
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.ActionEmptyException

class TransactionCreateDTO(

    var amountOperation: Double?,
    var trade: TradeDTO?,
    var shippingAddress: String?,
    var action: String?,
) {

    fun toModel(): Transaction {
        val transaction = Transaction()
        transaction.amountOperation = this.amountOperation
        transaction.trade = transaction.trade
        transaction.shippingAddress = this.shippingAddress
        transaction.action = this.verifyAction(this.action)
        return transaction
    }

    companion object {
        fun fromModel(transaction: Transaction) =
            TransactionCreateDTO(
                amountOperation = transaction.amountOperation,
                trade = TradeDTO(transaction.trade!!.user!!.id,
                    CryptoSimpleDTO(
                        transaction.trade!!.crypto!!.id,
                        transaction.trade!!.crypto!!.name,
                        transaction.trade!!.crypto!!.price),
                    transaction.trade!!.quantity,
                    transaction.trade!!.amountARS,
                    UserSimpleDTO(transaction.trade!!.user!!.id,
                        transaction.trade!!.user!!.name,
                        transaction.trade!!.user!!.lastName),
                    transaction.trade!!.operation.toString()),
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