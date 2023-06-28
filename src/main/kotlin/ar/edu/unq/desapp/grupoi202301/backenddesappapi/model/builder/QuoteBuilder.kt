package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote

class QuoteBuilder {
    var name: CryptoName? = null
    var price: Double? = null
    var time: String? = null

    fun build(): Quote {
        var quote = Quote()
        quote.cryptoName = this.name
        quote.price = this.price
        quote.time = this.time
        return quote
    }

    fun withName(name: CryptoName?): QuoteBuilder {
        this.name = name
        return this
    }

    fun withPrice(price: Double?): QuoteBuilder {
        this.price = price
        return this
    }

    fun withTime(time: String?): QuoteBuilder {
        this.time = time
        return this
    }
}