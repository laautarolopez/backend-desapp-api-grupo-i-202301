package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class CryptoVolume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The time cannot be null.")
    @DateTimeFormat
    var timeRequest: LocalDateTime? = null

    @Column(nullable = false)
    @NotNull(message = "The amount USD cannot be null.")
    var amountUSD: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The amount ARS cannot be null.")
    var amountARS: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The crypto name cannot be null.")
    var cryptos: List<CryptoOperated>? = null
}