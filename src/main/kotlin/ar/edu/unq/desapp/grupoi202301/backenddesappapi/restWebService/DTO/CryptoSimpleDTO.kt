package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName

class CryptoSimpleDTO(
    var id: Long?,
    var name: CryptoName?
) {

    fun toModel(): Crypto {
        val crypto = Crypto()
        crypto.id = this.id
        crypto.name = this.name
        return crypto
    }

    companion object {
        fun fromModel(crypto: Crypto) =
            CryptoSimpleDTO(
                id = crypto.id,
                name = crypto.name
            )
    }

    constructor() : this(null, null)
}