package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The crypto cannot be null.")
    var crypto: Crypto? = null

    @Column(nullable = false)
    @NotNull(message = "The quantity cannot be null.")
    var quantity: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The amount cannot be null.")
    var amountARS: Double? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The user cannot be null.")
    var user: User? = null

    @Column(nullable = false)
    @NotNull(message = "The operation cannot be null.")
    var operation: OperationType? = null
}