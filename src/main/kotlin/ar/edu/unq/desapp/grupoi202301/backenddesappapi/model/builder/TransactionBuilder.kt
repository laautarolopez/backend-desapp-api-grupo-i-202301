package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*

class TransactionBuilder {
    var quotationCrypto: Double? = null
    var amountOperation: Double? = null
    var trade: Trade? =  null
    var shippingAddress: String? = null
    var action: ActionTransaction? = null

    fun build(): Transaction {
        var transaction = Transaction()
        transaction.quotationCrypto = this.quotationCrypto
        transaction.amountOperation = this.amountOperation
        transaction.trade = this.trade
        transaction.shippingAddress = this.shippingAddress
        transaction.action = this.action
        return transaction
    }

    fun withQuotationCrypto(quotationCrypto: Double?): TransactionBuilder {
        this.quotationCrypto = quotationCrypto
        return this
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

    fun withAction(action: ActionTransaction?): TransactionBuilder {
        this.action = action
        return this
    }
}