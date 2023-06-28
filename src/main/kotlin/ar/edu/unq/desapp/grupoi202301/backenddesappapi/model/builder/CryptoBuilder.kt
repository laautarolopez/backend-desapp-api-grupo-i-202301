package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName

class CryptoBuilder {
    var name: CryptoName? = null
    var price: Double? = null

    fun build(): Crypto {
        var crypto = Crypto()
        crypto.name = this.name
        crypto.price = this.price
        return crypto
    }

    fun withName(name: CryptoName?): CryptoBuilder {
        this.name = name
        return this
    }

    fun withPrice(price: Double?): CryptoBuilder {
        this.price = price
        return this
    }
}