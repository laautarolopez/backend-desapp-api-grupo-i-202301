package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.PriceResponse

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