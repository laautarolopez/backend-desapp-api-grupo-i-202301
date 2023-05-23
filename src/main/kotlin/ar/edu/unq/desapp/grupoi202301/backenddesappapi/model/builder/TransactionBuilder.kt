package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*

class TransactionBuilder {
    var amountOperation: Double? = null
    var trade: Trade? =  null
    var shippingAddress: String? = null

    fun build(): Transaction {
        var transaction = Transaction()
        transaction.trade = this.trade
        return transaction
    }

    fun withAmountOperation(amountOperation: Double?): TransactionBuilder {
        this.amountOperation = amountOperation
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
}