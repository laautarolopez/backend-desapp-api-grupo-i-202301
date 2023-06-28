package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*

class TransactionResponseDTO(
    var id: Long?,
    var trade: TradeResponseDTO?,
    var amountUSD: Double?,
    var buyer: UserResponseDTO?,
    var seller: UserResponseDTO?,
    var shippingAddress: String?,
    var status: TransactionStatus?,
    ) {

    companion object {
        fun fromModel(transaction: Transaction) =
            TransactionResponseDTO(
                id = transaction.id,
                trade = TradeResponseDTO.fromModel(transaction.trade!!),
                amountUSD = transaction.getAmountUSD(),
                buyer = UserResponseDTO.fromModel(transaction.buyer!!),
                seller = UserResponseDTO.fromModel(transaction.seller!!),
                shippingAddress = getShippingAddress(transaction),
                status = transaction.status
            )

        private fun getShippingAddress(transaction: Transaction): String {
            if(transaction.trade!!.operation == OperationType.BUY) {
                return transaction.trade!!.user!!.walletAddress!!
            } else {
                return transaction.trade!!.user!!.cvuMercadoPago!!
            }
        }
    }

    constructor() : this(null, null, null, null, null, null, null)
}