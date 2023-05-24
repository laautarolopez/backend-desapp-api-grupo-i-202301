package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity(name = "trades")
class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The crypto cannot be null.")
    var crypto: Crypto? = null

    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "The cryptoPrice cannot be negative.")
    var cryptoPrice: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The quantity cannot be null.")
    @DecimalMin(value = "0.0", message = "The quantity cannot be negative.")
    var quantity: Double? = null

    @Column
    @DecimalMin(value = "0.0", message = "The amountARS cannot be negative.")
    var amountARS: Double? = null

    @ManyToOne
    @NotNull(message = "The user cannot be null.")
    var user: User? = null

    @Column(nullable = false)
    @NotNull(message = "The operation cannot be null.")
    var operation: OperationType? = null

    @Column(nullable = false)
    @NotNull(message = "The creation date cannot be null.")
    var creationDate: LocalDateTime? = null

    @Column(nullable = false)
    @NotNull(message = "The isActive cannot be null.")
    var isActive: Boolean? = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Trade

        if (id != other.id) return false
        if (crypto != other.crypto) return false
        if (cryptoPrice != other.cryptoPrice) return false
        if (quantity != other.quantity) return false
        if (user != other.user) return false
        if (operation != other.operation) return false
        if (creationDate!!.withNano(100) != other.creationDate!!.withNano(100)) return false
        return isActive == other.isActive
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (crypto?.hashCode() ?: 0)
        result = 31 * result + (cryptoPrice?.hashCode() ?: 0)
        result = 31 * result + (quantity?.hashCode() ?: 0)
        result = 31 * result + (user?.hashCode() ?: 0)
        result = 31 * result + (operation?.hashCode() ?: 0)
        result = 31 * result + (creationDate?.hashCode() ?: 0)
        result = 31 * result + (isActive?.hashCode() ?: 0)
        return result
    }
}