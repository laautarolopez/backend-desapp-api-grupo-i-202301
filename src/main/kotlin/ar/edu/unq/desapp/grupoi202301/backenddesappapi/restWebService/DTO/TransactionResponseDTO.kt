package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.ActionEmptyException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.CryptoEmptyException

class TransactionResponseDTO(
    var id: Long?,
    var crypto: String?,
    var quantity: Double?,
    var amountOperation: Double?,
    var user: UserSimpleDTO?,
    var numberOperations: Int?,
    var reputation: Int?,
    var shippingAddress: String?,
    var action: String?,
    ) {

    fun toModel(): Transaction {
        val transaction = Transaction()
        transaction.id = this.id
        transaction.trade!!.crypto!!.name = this.verifyCrypto(this.crypto)
        transaction.trade!!.quantity = this.quantity
        transaction.amountOperation = this.amountOperation
        transaction.trade!!.user = this.user!!.toModel()
        transaction.trade!!.user!!.operations = this.numberOperations
        transaction.trade!!.user!!.reputation = this.reputation
        transaction.shippingAddress = this.shippingAddress
        transaction.action = this.verifyAction(this.action)
        return transaction
    }

    companion object {
        fun fromModel(transaction: Transaction) =
            TransactionResponseDTO(
                id = transaction.id,
                crypto = transaction.trade!!.crypto!!.name.toString(),
                quantity = transaction.trade!!.quantity,
                amountOperation = transaction.amountOperation,
                user = UserSimpleDTO(transaction.trade!!.user!!.id, transaction.trade!!.user!!.name, transaction.trade!!.user!!.lastName),
                numberOperations =  transaction.trade!!.user!!.operations,
                reputation = transaction.trade!!.user!!.reputation,
                shippingAddress = transaction.shippingAddress,
                action = transaction.action.toString()
            )
    }

    private fun verifyCrypto(crypto: String?): CryptoName {
        var newCrypto: CryptoName
        when(crypto) {
            "ALICEUSDT" -> newCrypto = CryptoName.ALICEUSDT
            "MATICUSDT" -> newCrypto = CryptoName.MATICUSDT
            "AXSUSDT" -> newCrypto = CryptoName.AXSUSDT
            "AAVEUSDT" -> newCrypto = CryptoName.AAVEUSDT
            "ATOMUSDT" -> newCrypto = CryptoName.ATOMUSDT
            "NEOUSDT" -> newCrypto = CryptoName.NEOUSDT
            "DOTUSDT" -> newCrypto = CryptoName.DOTUSDT
            "ETHUSDT" -> newCrypto = CryptoName.ETHUSDT
            "CAKEUSDT" -> newCrypto = CryptoName.CAKEUSDT
            "BTCUSDT" -> newCrypto = CryptoName.BTCUSDT
            "BNBUSDT" -> newCrypto = CryptoName.BNBUSDT
            "ADAUSDT" -> newCrypto = CryptoName.ADAUSDT
            "TRXUSDT" -> newCrypto = CryptoName.TRXUSDT
            "AUDIOUSDT" -> newCrypto = CryptoName.AUDIOUSDT
            else -> {
                throw CryptoEmptyException()
            }
        }
        return newCrypto
    }

    private fun verifyAction(action: String?): ActionTransaction {
        var newAction: ActionTransaction
        when(action) {
            "Make" -> newAction = ActionTransaction.MAKE
            "Confirm" -> newAction = ActionTransaction.CONFIRM
            "Cancel" -> newAction = ActionTransaction.CANCEL
            else -> {
                throw ActionEmptyException()
            }
        }
        return newAction
    }
}