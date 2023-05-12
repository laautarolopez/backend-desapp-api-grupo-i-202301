package ar.edu.unq.desapp.grupoi202301.backenddesappapi.RESTwebservice.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import java.time.LocalDateTime

class CryptoDTO(
    var id: Long?,
    var name: CryptoName?,
    var time: LocalDateTime?,
    var price: Double? = null
) {

    fun toModel(): Crypto {
        val crypto = Crypto()
        crypto.id = this.id
        crypto.name = this.name
        crypto.time = this.time
        crypto.price = this.price
        return crypto
    }

    companion object {
        fun fromModel(crypto: Crypto) =
            CryptoDTO(
                id = crypto.id,
                name = crypto.name,
                time = crypto.time,
                price = crypto.price,
            )
    }
}