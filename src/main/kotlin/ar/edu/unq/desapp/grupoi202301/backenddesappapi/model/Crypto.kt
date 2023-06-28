package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull

@Entity(name = "cryptos")
class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The crypto name cannot be null.")
    var name: CryptoName? = null

    @Column
    @DecimalMin(value = "0.0", message = "The price cannot be negative.")
    var price: Double? = null

    @Column
    var time: String? = null

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    var quotes24hs: List<Quote24hs> = emptyList()

    fun addQuote(quote: Quote24hs) {
        quotes24hs += quote
    }

    fun removeQuote(quote: Quote24hs) {
        quotes24hs -= quote
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