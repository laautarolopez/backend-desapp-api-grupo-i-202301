package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

class Transaction {
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
    @NotNull(message = "The quotation crypto cannot be null.")
    var quotationCrypto: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The amount of operation cannot be null.")
    var amountOperation: Double? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The user cannot be null.")
    var user: User? = null

    @Column(nullable = false)
    @NotNull(message = "The numbers of operations cannot be null.")
    var numberOperations: Int? = null

    @Column(nullable = false)
    @NotNull(message = "The reputation cannot be null.")
    var reputation: Int? = null

    @Column(nullable = false)
    @NotNull(message = "The shipping address cannot be null.")
    var shippingAddress: String? = null

    @Column(nullable = false)
    @NotNull(message = "The action cannot be null.")
    var action: ActionTransaction? = null
}