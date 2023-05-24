package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity(name = "transactions")
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    fun getAmountUSD(): Double {
        val quantity = this.trade!!.quantity
        val cryptoPrice = this.trade!!.cryptoPrice
        return quantity!! * cryptoPrice!!
    }

    @Column(nullable = false)
    @NotNull(message = "The idUserRequested cannot be null.")
    var idUserRequested: Long? = null

    @ManyToOne
    var buyer: User? = null

    @ManyToOne
    var seller: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The trade cannot be null.")
    var trade: Trade? = null

    @Column(nullable = false)
    @NotNull(message = "The status cannot be null.")
    var status: TransactionStatus = TransactionStatus.CREATED
}