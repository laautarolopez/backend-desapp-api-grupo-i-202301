package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote24hs

class Quote24DTO(
    var price: Double,
    var time: String
) {

    companion object {
        fun fromModel(quote24hs: Quote24hs) =
            Quote24DTO(
                price = quote24hs.price!!,
                time = quote24hs.time!!
            )
    }
}