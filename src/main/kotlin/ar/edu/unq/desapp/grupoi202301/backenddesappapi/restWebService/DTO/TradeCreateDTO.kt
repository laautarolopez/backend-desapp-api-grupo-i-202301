package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.OperationType
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.OperationEmptyException

class TradeCreateDTO(
    var crypto: CryptoSimpleDTO?,
    //var quantity: Double?,
    var amountARS: Double?,
    var user: UserSimpleDTO?,
    var operation: String?,
    var isActive: Boolean?
) {

    fun toModel(): Trade {
        val trade = Trade()
        trade.crypto = this.crypto!!.toModel()
        trade.amountARS = this.amountARS
        trade.user = this.user!!.toModel()
        trade.operation = this.verifyOperation(this.operation)
        trade.isActive = this.isActive
        return trade
    }

    companion object {
        fun fromModel(trade: Trade) =
            TradeCreateDTO(
                crypto = CryptoSimpleDTO(trade.crypto!!.id, trade.crypto!!.name),
//                quantity = trade.crypto!!.price,
                amountARS = trade.amountARS,
                user = UserSimpleDTO(trade.user!!.id, trade.user!!.name, trade.user!!.lastName),
                operation = trade.operation.toString(),
                isActive = trade.isActive
            )
    }

    private fun verifyOperation(operation: String?): OperationType {
        var newOperation: OperationType
        when (operation) {
            "BUY" -> newOperation = OperationType.BUY
            "SALE" -> newOperation = OperationType.SALE
            else -> {
                throw OperationEmptyException()
            }
        }
        return newOperation
    }
}