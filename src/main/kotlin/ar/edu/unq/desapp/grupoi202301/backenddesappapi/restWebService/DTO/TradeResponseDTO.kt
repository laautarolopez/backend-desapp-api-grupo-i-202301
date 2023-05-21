package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.OperationType
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import java.time.LocalDateTime

class TradeResponseDTO(
    var id: Long?,
    var date: LocalDateTime?,
    var crypto: CryptoSimpleDTO,
    var quantity: Double?,
    var amountARS: Double?,
    var user: UserSimpleDTO?,
    var operation: OperationType?
) {

    companion object {
        fun fromModel(trade: Trade) =
            TradeResponseDTO(
                id = trade.id,
                date = trade.creationDate,
                crypto = CryptoSimpleDTO.fromModel(trade.crypto!!),
                quantity = trade.quantity,
                amountARS = trade.amountARS,
                user = UserSimpleDTO(trade.user!!.id, trade.user!!.name, trade.user!!.lastName),
                operation = trade.operation
            )
    }
}