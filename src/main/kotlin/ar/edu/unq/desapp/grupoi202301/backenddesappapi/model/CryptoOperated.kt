package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull

@Entity(name = "cryptosOperated")
class CryptoOperated {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The crypto name cannot be null.")
    var cryptoName: CryptoName? = null

    @Column(nullable = false)
    @NotNull(message = "The quantity cannot be null.")
    @DecimalMin(value = "0.0", message = "The quantity cannot be negative.")
    var quantity: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The price cannot be null.")
    @DecimalMin(value = "0.0", message = "The price cannot be negative.")
    var price: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The amount ARS cannot be null.")
    @DecimalMin(value = "0.0", message = "The amount ARS cannot be negative.")
    var amountARS: Double? = null

    constructor(id: Long, crypto: CryptoName, quantity: Double, price: Double, amountARS: Double) {
        this.id = id
        this.cryptoName = crypto
        this.quantity = quantity
        this.price = price
        this.amountARS = amountARS
    }

    constructor() {}
}