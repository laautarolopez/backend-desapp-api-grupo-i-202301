package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName

class CryptoDTO(
    var id: Long?,
    var name: CryptoName?,
    var price: PriceDTO?
) {

    fun toModel(): Crypto {
        val crypto = Crypto()
        crypto.id = this.id
        crypto.name = this.name
        return crypto
    }

    companion object {
        fun fromModel(crypto: Crypto) {
            CryptoDTO(
                id = crypto.id,
                name = crypto.name,
                price = PriceDTO(crypto.price!!, crypto.time.toString())
            )
        }
    }
}