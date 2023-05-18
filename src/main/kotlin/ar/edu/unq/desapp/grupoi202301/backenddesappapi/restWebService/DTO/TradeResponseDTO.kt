package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import java.time.LocalDateTime

class TradeResponseDTO(
    val id: Long?,
    val date: LocalDateTime?,
    var crypto: String,
    var quantity: Double?,
    var amountARS: Double?,
    var user: UserSimpleDTO?,
    var operations: Int?,
    var reputation: Int?
) {

    companion object {
        fun fromModel(trade: Trade) =
            TradeResponseDTO(
                id = trade.id,
                date = trade.creationDate,
                crypto = trade.crypto!!.name.toString(),
                quantity = trade.quantity,
                amountARS = trade.amountARS,
                user = UserSimpleDTO(trade.user!!.id, trade.user!!.name, trade.user!!.lastName),
                operations = trade!!.user!!.operations,
                reputation = trade!!.user!!.reputation
            )
    }
}