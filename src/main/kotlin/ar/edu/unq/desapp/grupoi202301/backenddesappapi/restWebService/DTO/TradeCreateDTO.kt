package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.OperationType
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.OperationEmptyException

class TradeCreateDTO(
    var idCrypto: Long,
    var quantity: Double?,
    var amountARS: Double?,
    var idUser: Long,
    var operation: String?
) {

    fun toModel(): Trade {
        val trade = Trade()
        trade.crypto!!.id = this.idCrypto
        trade.quantity = this.quantity
        trade.amountARS = this.amountARS
        trade.user!!.id = this.idUser
        trade.operation = this.verifyOperation(this.operation)
        trade.isActive = true
        return trade
    }

    companion object {
//        fun fromModel(trade: Trade) =
//            TradeCreateDTO(
//                idCrypto = trade.crypto!!.id!!,
//                quantity = trade.quantity,
//                amountARS = trade.amountARS,
//                idUser = trade.user!!.id!!,
//                operation = trade.operation.toString()
//            )
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