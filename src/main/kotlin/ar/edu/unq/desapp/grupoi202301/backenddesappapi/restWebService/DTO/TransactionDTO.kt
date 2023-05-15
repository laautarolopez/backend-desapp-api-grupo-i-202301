package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.ActionEmptyException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.CryptoEmptyException

class TransactionDTO(
    var id: Long?,
    var crypto: String?,
    var quantity: Double?,
    var quotationCrypto: Double?,
    var amountOperation: Double?,
    var user: UserDTO?,
    var numberOperations: Int?,
    var reputation: Int?,
    var shippingAddress: String?,
    var action: String?,
    ) {

    fun toModel(): Transaction {
        val transaction = Transaction()
        transaction.id = this.id
        transaction.crypto = this.verifyCrypto(this.crypto)
        transaction.quantity = this.quantity
        transaction.quotationCrypto = this.quotationCrypto
        transaction.amountOperation = this.amountOperation
        transaction.user = this.user!!.toModel()
        transaction.numberOperations = this.numberOperations
        transaction.reputation = this.reputation
        transaction.shippingAddress = this.shippingAddress
        transaction.action = this.verifyAction(this.action)
        return transaction
    }

    companion object {
        fun fromModel(transaction: Transaction) =
            TransactionDTO(
                id = transaction.id,
                crypto = transaction.crypto.toString(),
                quantity = transaction.quantity,
                quotationCrypto = transaction.quotationCrypto,
                amountOperation = transaction.amountOperation,
                user = UserDTO(transaction.user!!.id, transaction.user!!.name, transaction.user!!.lastName, transaction.user!!.email, transaction.user!!.address, transaction.user!!.password, transaction.user!!.cvuMercadoPago, transaction.user!!.walletAddress),
                numberOperations =  transaction.numberOperations,
                reputation = transaction.reputation,
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