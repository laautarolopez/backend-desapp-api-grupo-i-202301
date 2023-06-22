package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity(name = "volumens")
class Volume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The idUser cannot be null.")
    var idUser: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The date cannot be null.")
    var date: LocalDateTime? = null

    @Column(nullable = false)
    @NotNull(message = "The amountUSD cannot be null.")
    @DecimalMin(value = "0.0", message = "The amountUSD cannot be negative.")
    var amountUSD: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The amountARS cannot be null.")
    @DecimalMin(value = "0.0", message = "The amountARS cannot be negative.")
    var amountARS: Double? = null

    @OneToMany
    var cryptos: List<CryptoOperated>? = null

    constructor(idUser: Long, date: LocalDateTime, amountUSD: Double, amountARS: Double, cryptos: List<CryptoOperated>) {
        this.idUser = idUser
        this.date = date
        this.amountUSD = amountUSD
        this.amountARS = amountARS
        this.cryptos = cryptos
    }

    constructor() {}
}