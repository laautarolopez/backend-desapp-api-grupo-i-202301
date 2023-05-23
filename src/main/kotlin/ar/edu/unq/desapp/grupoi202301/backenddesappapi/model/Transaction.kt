package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.DolarResponse
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

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

    fun getAmountARS(): Double {
        val amountUSD = this.getAmountUSD()
        val DolarBlue = DolarResponse().getPrice()
        return amountUSD * DolarBlue.price
    }

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