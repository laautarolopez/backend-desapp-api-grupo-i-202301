package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

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
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    var buyer: User? = null

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    var seller: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The trade cannot be null.")
    var trade: Trade? = null

    @Column(nullable = false)
    @NotNull(message = "The status cannot be null.")
    var status: TransactionStatus = TransactionStatus.CREATED

    @Column
    var date: LocalDateTime? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Transaction

        if (id != other.id) return false
        if (idUserRequested != other.idUserRequested) return false
        if (buyer != other.buyer) return false
        if (seller != other.seller) return false
        if (trade != other.trade) return false
        return status == other.status
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (idUserRequested?.hashCode() ?: 0)
        result = 31 * result + (buyer?.hashCode() ?: 0)
        result = 31 * result + (seller?.hashCode() ?: 0)
        result = 31 * result + (trade?.hashCode() ?: 0)
        result = 31 * result + status.hashCode()
        return result
    }
}