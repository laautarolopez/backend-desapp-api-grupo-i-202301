package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull

@Entity(name = "quotes")
class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The cryptoname cannot be null.")
    var cryptoName: CryptoName? = null

    @Column
    @DecimalMin(value = "0.0", message = "The price cannot be negative.")
    var price: Double? = null

    @Column
    var time: String? = null
}