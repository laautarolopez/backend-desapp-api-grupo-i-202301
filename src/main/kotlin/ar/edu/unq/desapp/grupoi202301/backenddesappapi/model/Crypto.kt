package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity(name = "cryptos")
class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The crypto name cannot be null.")
    var name: CryptoName? = null

    @Column
    @DateTimeFormat
    var time: LocalDateTime? = null

    @Column
    @DecimalMin(value = "0.0", message = "The price cannot be negative.")
    var price: Double? = null
}