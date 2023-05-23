package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity(name = "transactions")
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The amount of operation cannot be null.")
    @DecimalMin(value = "0.0", message = "The amount of operation cannot be negative.")
    var amountUSD: Double? = null

    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "The amount of operation cannot be negative.")
    var amountARS: Double? = null

    @Column(nullable = false)
    @NotNull
    var idUserRequested: Long? = null

    @ManyToOne
    @NotNull(message = "The buyer cannot be null.")
    var buyer: User? = null

    @ManyToOne
    @NotNull(message = "The buyer cannot be null.")
    var seller: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The trade cannot be null.")
    var trade: Trade? = null

    @Column(nullable = false)
    var status: TransactionStatus = TransactionStatus.CREATED
}