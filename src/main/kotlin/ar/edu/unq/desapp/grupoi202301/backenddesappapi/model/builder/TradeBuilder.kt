package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*
import java.time.LocalDateTime

class TradeBuilder {
    var crypto: Crypto? = null
    var cryptoPrice: Double? = null
    var quantity: Double? = null
    var user: User? = null
    var operation: OperationType? = null
    var creationDate: LocalDateTime? = null
    var isActive: Boolean? = null

    fun build(): Trade {
        var trade = Trade()
        trade.crypto = this.crypto
        trade.cryptoPrice = this.cryptoPrice
        trade.quantity = this.quantity
        trade.user = this.user
        trade.operation = this.operation
        trade.creationDate = this.creationDate
        trade.isActive = this.isActive
        return trade
    }

    fun withCrypto(crypto: Crypto?): TradeBuilder {
        this.crypto = crypto
        return this
    }

    fun withCryptoPrice(crypto: Double?): TradeBuilder {
        this.cryptoPrice = crypto
        return this
    }


    fun withQuantity(quantity: Double?): TradeBuilder {
        this.quantity = quantity
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

    fun withCreationDate(date: LocalDateTime?): TradeBuilder {
        this.creationDate = date
        return this
    }

    fun withIsActive(active: Boolean?): TradeBuilder {
        this.isActive = active
        return this
    }
}