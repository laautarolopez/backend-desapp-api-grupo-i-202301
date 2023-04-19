package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*

class TradeBuilder {
    var crypto: Crypto? = null
    var quantity: Double? = null
    var amountARS: Double? = null
    var userName: String? = null
    var userLastName: String? = null
    var operation: OperationType? = null

    fun build(): Trade {
        var trade = Trade()
        trade.crypto = this.crypto
        trade.quantity = this.quantity
        trade.amountARS = this.amountARS
        trade.userName = this.userName
        trade.userLastName = this.userLastName
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

    fun withUserName(userName: String?): TradeBuilder {
        this.userName = userName
        return this
    }

    fun withUserLastName(userLastName: String?): TradeBuilder {
        this.userLastName = userLastName
        return this
    }

    fun withOperation(operation: OperationType?): TradeBuilder {
        this.operation = operation
        return this
    }
}