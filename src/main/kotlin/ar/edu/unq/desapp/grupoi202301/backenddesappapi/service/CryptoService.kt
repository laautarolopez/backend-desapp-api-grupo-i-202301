package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.PriceResponse
import jakarta.validation.Valid

interface CryptoService {

    fun create(@Valid crypto: Crypto): Crypto

    fun getCrypto(idCrypto: Long?): Crypto

    fun getPrice(cryptoName: String): PriceResponse

    fun getPrices(): List<PriceResponse>

    fun clear()
}