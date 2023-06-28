package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote24hs
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.PriceResponse
import jakarta.validation.Valid

interface CryptoService {

    fun create(@Valid crypto: Crypto): Crypto

    fun update(@Valid crypto: Crypto): Crypto

    fun getCrypto(idCrypto: Long?): Crypto

    fun getPrice(cryptoName: String): PriceResponse

    fun getPrices(): List<PriceResponse>

    fun getQuotes24hs(cryptoName: String) : List<Quote24hs>

    fun findByName(name: CryptoName): Crypto

    fun updateQuotes24hs(crypto: Crypto)

    fun clear()
}