package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity(name = "CryptosVolume")
class CryptoVolume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The time request cannot be null.")
    @DateTimeFormat
    var timeRequest: LocalDateTime? = null

    @Column(nullable = false)
    @NotNull(message = "The amount USD cannot be null.")
    @DecimalMin(value = "0.0", message = "The amount USD cannot be negative.")
    var amountUSD: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The amount ARS cannot be null.")
    @DecimalMin(value = "0.0", message = "The amount ARS cannot be negative.")
    var amountARS: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The crypto name cannot be null.")
    @OneToMany(fetch = FetchType.LAZY)
    var cryptos: List<CryptoOperated>? = null
}