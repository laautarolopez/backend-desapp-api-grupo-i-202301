package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.PriceResponse
import java.time.LocalDateTime

class PriceDTO(
    var price: Double,
    var time: String
) {

    companion object {
        fun fromModel(priceResponse: PriceResponse) =
            PriceDTO(
                price = priceResponse.price,
                time = priceResponse.time
            )
    }
}