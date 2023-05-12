package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*

class TransactionBuilder {
    var crypto: CryptoName? = null
    var quantity: Double? = null
    var quotationCrypto: Double? = null
    var amountOperation: Double? = null
    var user: User? = null
    var numberOperations: Int? = null
    var reputation: Int? = null
    var trade: Trade? =  null
    var shippingAddress: String? = null
    var action: ActionTransaction? = null

    fun build(): Transaction {
        var transaction = Transaction()
        transaction.crypto = this.crypto
        transaction.quantity = this.quantity
        transaction.quotationCrypto = this.quotationCrypto
        transaction.amountOperation = this.amountOperation
        transaction.user = this.user
        transaction.numberOperations = this.numberOperations
        transaction.reputation = this.reputation
        transaction.trade = this.trade
        transaction.shippingAddress = this.shippingAddress
        transaction.action = this.action
        return transaction
    }

    fun withCrypto(crypto: CryptoName?): TransactionBuilder {
        this.crypto = crypto
        return this
    }

    fun withQuantity(quantity: Double?): TransactionBuilder {
        this.quantity = quantity
        return this
    }

    fun withQuotationCrypto(quotationCrypto: Double?): TransactionBuilder {
        this.quotationCrypto = quotationCrypto
        return this
    }

    fun withAmountOperation(amountOperation: Double?): TransactionBuilder {
        this.amountOperation = amountOperation
        return this
    }

    fun withUser(user: User?): TransactionBuilder {
        this.user = user
        return this
    }

    fun withNumberOperations(numberOperations: Int?): TransactionBuilder {
        this.numberOperations = numberOperations
        return this
    }

    fun withReputation(reputation: Int?): TransactionBuilder {
        this.reputation = reputation
        return this
    }

    fun withTrade(trade: Trade?): TransactionBuilder {
        this.trade = trade
        return this
    }

    fun withShippingAddress(shippingAddress: String?): TransactionBuilder {
        this.shippingAddress = shippingAddress
        return this
    }

    fun withAction(action: ActionTransaction?): TransactionBuilder {
        this.action = action
        return this
    }
}