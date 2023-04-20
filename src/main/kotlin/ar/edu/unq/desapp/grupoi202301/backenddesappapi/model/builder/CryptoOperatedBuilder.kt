package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoOperated

class CryptoOperatedBuilder {
    var cryptoName: CryptoName? = null
    var quantity: Double? = null
    var price: Double? = null
    var amountARS: Double? = null

    fun build(): CryptoOperated {
        var cryptoOperated = CryptoOperated()
        cryptoOperated.cryptoName = this.cryptoName
        cryptoOperated.quantity = this.quantity
        cryptoOperated.price = this.price
        cryptoOperated.amountARS = this.amountARS
        return cryptoOperated
    }

    fun withCryptoName(cryptoName: CryptoName?): CryptoOperatedBuilder {
        this.cryptoName = cryptoName
        return this
    }

    fun withQuantity(quantity: Double?): CryptoOperatedBuilder {
        this.quantity = quantity
        return this
    }

    fun withPrice(price: Double?): CryptoOperatedBuilder {
        this.price = price
        return this
    }

    fun withAmount(amount: Double?): CryptoOperatedBuilder {
        this.amountARS = amount
        return this
    }
}