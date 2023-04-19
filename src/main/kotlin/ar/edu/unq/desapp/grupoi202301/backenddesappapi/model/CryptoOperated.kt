package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull

class CryptoOperated {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The crypto name cannot be null.")
    var cryptoName: CryptoName? = null

    @Column(nullable = false)
    @NotNull(message = "The quantity cannot be null.")
    var quantity: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The price cannot be null.")
    var price: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The amount ARS cannot be null.")
    var amountARS: Double? = null
}