package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*

class TradeBuilder {
    var crypto: Crypto? = null
    var quantity: Double? = null
    var amountARS: Double? = null
    var user: User? = null
    var operation: OperationType? = null

    fun build(): Trade {
        var trade = Trade()
        trade.crypto = this.crypto
        trade.quantity = this.quantity
        trade.amountARS = this.amountARS
        trade.user = this.user
        trade.operation = this.operation
        return trade
    }

    fun withCrypto(crypto: Crypto?): TradeBuilder {
        this.crypto = crypto
        return this
    }

    fun withQuantity(quantity: Double?): TradeBuilder {
        this.quantity = quantity
        return this
    }

    fun withAmountARS(amountARS: Double?): TradeBuilder {
        this.amountARS = amountARS
        return this
    }

    fun withUser(user: User?): TradeBuilder {
        this.user = user
        return this
    }

    fun withOperation(operation: OperationType?): TradeBuilder {
        this.operation = operation
        return this
    }
}