package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction

class TransactionCreateDTO(
    var idUserRequested: Long?,
    var idTrade: Long?
) {

    fun toModel(): Transaction {
        val transaction = Transaction()
        transaction.idUserRequested = idUserRequested
        val trade = Trade()
        trade.id = this.idTrade
        transaction.trade = trade
        return transaction
    }
}