package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName

class CryptoCreateDTO(
    var name: CryptoName?
) {

    fun toModel(): Crypto {
        val crypto = Crypto()
        crypto.name = this.name
        return crypto
    }

    companion object {
        fun fromModel(crypto: Crypto) =
            CryptoCreateDTO(
                name = crypto.name,
            )
    }
}