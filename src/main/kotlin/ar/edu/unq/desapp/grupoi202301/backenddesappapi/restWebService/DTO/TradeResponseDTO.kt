package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.OperationType
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import java.time.LocalDateTime

class TradeResponseDTO(
    var id: Long?,
    var crypto: CryptoSimpleDTO,
    var cryptoPrice: Double?,
    var quantity: Double?,
    var amountARS: Double?,
    var user: UserSimpleDTO?,
    var operation: OperationType?,
    var isActive: Boolean?
) {

    companion object {
        fun fromModel(trade: Trade) =
            TradeResponseDTO(
                id = trade.id,
                crypto = CryptoSimpleDTO.fromModel(trade.crypto!!),
                cryptoPrice = trade.cryptoPrice,
                quantity = trade.quantity,
                amountARS = trade.amountARS,
                user = UserSimpleDTO(trade.user!!.id, trade.user!!.name, trade.user!!.lastName),
                operation = trade.operation,
                isActive = trade.isActive
            )
    }
}