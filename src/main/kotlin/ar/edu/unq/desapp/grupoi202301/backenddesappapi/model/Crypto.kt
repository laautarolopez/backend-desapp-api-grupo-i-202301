package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.BinanceResponse
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.PriceResponse
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity(name = "cryptos")
class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The crypto name cannot be null.")
    var name: CryptoName? = null

    fun getPrice(): PriceResponse {
        return BinanceResponse().getPrice(name.toString())
    }
}