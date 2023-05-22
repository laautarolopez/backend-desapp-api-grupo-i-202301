package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import java.time.LocalDateTime

class CryptoBuilder {
    var name: CryptoName? = null
    var time: LocalDateTime? = null
    var price: Double? = null

    fun build(): Crypto {
        var crypto = Crypto()
        crypto.name = this.name
        return crypto
    }

    fun withName(name: CryptoName?): CryptoBuilder {
        this.name = name
        return this
    }

    fun withTime(time: LocalDateTime?): CryptoBuilder {
        this.time = time
        return this
    }

    fun withPrice(price: Double?): CryptoBuilder {
        this.price = price
        return this
    }
}