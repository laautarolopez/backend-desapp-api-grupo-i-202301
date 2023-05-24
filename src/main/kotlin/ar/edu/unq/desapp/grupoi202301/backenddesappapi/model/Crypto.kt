package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.BinanceResponse
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.PriceResponse
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Crypto

        if (id != other.id) return false
        return name == other.name
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }


}