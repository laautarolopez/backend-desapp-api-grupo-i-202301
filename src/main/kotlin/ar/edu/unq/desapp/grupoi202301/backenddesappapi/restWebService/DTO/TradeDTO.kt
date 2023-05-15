package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.OperationType
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.OperationEmptyException

class TradeDTO(
    val id: Long?,
    var crypto: CryptoDTO?,
    var quantity: Double?,
    var amountARS: Double?,
    var user: UserSimpleDTO?,
    var operation: String?
    ) {

    fun toModel(): Trade {
        val trade = Trade()
        trade.id = this.id
        trade.crypto = this.crypto!!.toModel()
        trade.quantity = this.quantity
        trade.amountARS = this.amountARS
        trade.user = this.user!!.toModel()
        trade.operation = this.verifyOperation(this.operation)
        return trade
    }

    companion object {
        fun fromModel(trade: Trade) =
            TradeDTO(
                id = trade.id,
                crypto = CryptoDTO(trade.crypto!!.id, trade.crypto!!.name, trade.crypto!!.time, trade.crypto!!.price),
                quantity = trade.quantity,
                amountARS = trade.amountARS,
                user = UserSimpleDTO(trade.user!!.id, trade.user!!.name, trade.user!!.lastName),
                operation = trade.operation.toString()
            )
    }

    private fun verifyOperation(operation: String?): OperationType {
        var newOperation: OperationType
        when(operation) {
            "Buy" -> newOperation = OperationType.BUY
            "Sale" -> newOperation = OperationType.SALE
            else -> {
                throw OperationEmptyException()
            }
        }
        return newOperation
    }
}