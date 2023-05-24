package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.OperationType
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.OperationEmptyException
import java.time.LocalDateTime

class TradeCreateDTO(
    var idCrypto: Long,
    var quantity: Double?,
    var idUser: Long,
    var operation: String?
) {

    fun toModel(): Trade {
        val trade = Trade()
        val crypto = Crypto()
        crypto.id = this.idCrypto
        trade.crypto = crypto
        trade.quantity = this.quantity
        val user = User()
        user.id = this.idUser
        trade.user = user
        trade.operation = this.verifyOperation(this.operation)
        trade.creationDate = LocalDateTime.now()
        trade.isActive = true
        return trade
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