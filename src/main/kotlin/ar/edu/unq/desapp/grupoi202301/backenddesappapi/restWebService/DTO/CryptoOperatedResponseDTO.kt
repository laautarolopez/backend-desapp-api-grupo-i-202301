package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoOperated

class CryptoOperatedResponseDTO(
    var id: Long?,
    var cryptoName: String?,
    var quantity: Double?,
    var price: Double?,
    var amountARS: Double? = null
) {

    companion object {
        fun fromModel(crypto: CryptoOperated) =
            CryptoOperatedResponseDTO(
                id = crypto.id,
                cryptoName = crypto.cryptoName.toString(),
                quantity = crypto.quantity,
                price = crypto.price,
                amountARS = crypto.amountARS,
            )
    }
}