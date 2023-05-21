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

//    @Column(nullable = false)
//    @NotNull(message = "The quantity cannot be null.")
//    @DecimalMin(value = "0.0", message = "The quantity cannot be negative.")
//    var quantity: Double? = null
    // TODO: Revisar si quantity hace falta, cotizacion ya se encuentra en el crypto

    @Column(nullable = false)
    @NotNull(message = "The amount cannot be null.")
    @DecimalMin(value = "0.0", message = "The amount cannot be negative.")
    var amountARS: Double? = null

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