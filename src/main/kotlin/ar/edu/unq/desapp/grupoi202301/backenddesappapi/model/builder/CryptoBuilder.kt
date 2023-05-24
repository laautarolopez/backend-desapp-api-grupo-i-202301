package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName

class CryptoBuilder {
    var name: CryptoName? = null

    fun build(): Crypto {
        var crypto = Crypto()
        crypto.name = this.name
        return crypto
    }

    fun withName(name: CryptoName?): CryptoBuilder {
        this.name = name
        return this
    }
}