package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.PriceResponse
import java.time.LocalDateTime

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
            val cryptoPrice = crypto.getPrice()
            CryptoDTO(
                id = crypto.id,
                name = crypto.name,
                price = PriceDTO(cryptoPrice.price, cryptoPrice.time)
            )
        }
    }
}