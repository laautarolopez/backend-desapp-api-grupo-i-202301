package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    var name: CryptoName? = null

    @Column(nullable = false)
    @NotNull(message = "The time cannot be null.")
    @DateTimeFormat
    var time: LocalDateTime? = null

    @Column(nullable = false)
    @NotNull(message = "The price cannot be null.")
    var price: Double? = null
}