package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.DolarResponse
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

    fun getAmountARS(): Double {
        val price = this.quantity!! * this.cryptoPrice!!
        val DolarBlue = DolarResponse().getPrice()
        return price * DolarBlue.price
    }

    @ManyToOne
    @NotNull(message = "The user cannot be null.")
    var user: User? = null

    @Column(nullable = false)
    @NotNull(message = "The operation cannot be null.")
    var operation: OperationType? = null

    @Column(nullable = false)
    var creationDate: LocalDateTime? = LocalDateTime.now()

    @Column(nullable = false)
    var isActive: Boolean? = true
}