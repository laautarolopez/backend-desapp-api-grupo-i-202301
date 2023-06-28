package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull

@Entity(name = "quotes")
class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    var id: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The crypto name cannot be null.")
    var cryptoName: CryptoName? = null

    @Column
    @DecimalMin(value = "0.0", message = "The price cannot be negative.")
    var price: Double? = null

    @Column
    var time: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Quote

        if (id != other.id) return false
        if (cryptoName != other.cryptoName) return false
        if (price != other.price) return false
        return time == other.time
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (cryptoName?.hashCode() ?: 0)
        result = 31 * result + (price?.hashCode() ?: 0)
        result = 31 * result + (time?.hashCode() ?: 0)
        return result
    }
}