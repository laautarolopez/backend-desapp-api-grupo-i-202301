package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.OperationType
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import java.time.LocalDateTime

class TradeActiveDTO(
    var id: Long?,
    var date: LocalDateTime?,
    var crypto: CryptoSimpleDTO,
    var cryptoPrice: Double?,
    var quantity: Double?,
    var amountARS: Double?,
    var user: UserResponseDTO?,
    var operation: OperationType?,
    var isActive: Boolean?
) {

    companion object {
        fun fromModel(trade: Trade) =
            TradeActiveDTO(
                id = trade.id,
                date = trade.creationDate,
                crypto = CryptoSimpleDTO.fromModel(trade.crypto!!),
                cryptoPrice = trade.cryptoPrice,
                quantity = trade.quantity,
                amountARS = trade.amountARS,
                user = UserResponseDTO(
                    trade.user!!.id,
                    trade.user!!.name,
                    trade.user!!.lastName,
                    trade.user!!.operations,
                    trade.user!!.getReputation()
                ),
                operation = trade.operation,
                isActive = trade.isActive
            )
    }
}