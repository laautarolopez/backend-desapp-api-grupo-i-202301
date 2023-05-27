package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*
import java.time.LocalDateTime

class TransactionBuilder {
    var idUserRequested: Long? = null
    var buyer: User? = null
    var seller: User? = null
    var trade: Trade? =  null
    var status: TransactionStatus = TransactionStatus.CREATED
    var date: LocalDateTime? = null

    fun build(): Transaction {
        var transaction = Transaction()
        transaction.idUserRequested = this.idUserRequested
        transaction.buyer = this.buyer
        transaction.trade = this.trade
        transaction.seller = this.seller
        transaction.status = this.status
        transaction.date = this.date
        return transaction
    }

    fun withIdUserRequested(idUserRequested: Long?): TransactionBuilder {
        this.idUserRequested = idUserRequested
        return this
    }

    fun withBuyer(buyer: User?): TransactionBuilder {
        this.buyer = buyer
        return this
    }

    fun withSeller(seller: User?): TransactionBuilder {
        this.seller = seller
        return this
    }

    fun withTrade(trade: Trade?): TransactionBuilder {
        this.trade = trade
        return this
    }

    fun withStatus(status: TransactionStatus): TransactionBuilder {
        this.status = status
        return this
    }

    fun withDate(date: LocalDateTime?): TransactionBuilder {
        this.date = date
        return this
    }
}