package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

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

    //@ManyToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    @NotNull(message = "The username cannot be null.")
    @Size(min = 3, max = 30, message = "The name must be between 3 and 30 characters long.")
    var userName: String? = null

    @Column(nullable = false)
    @NotNull(message = "The user lastname cannot be null.")
    @Size(min = 3, max = 30, message = "The last name must be between 3 and 30 characters long.")
    var userLastName: String? = null

    @Column(nullable = false)
    @NotNull(message = "The operation cannot be null.")
    var operation: OperationType? = null
}