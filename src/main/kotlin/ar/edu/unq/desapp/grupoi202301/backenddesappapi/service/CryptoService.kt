package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import jakarta.validation.Valid

interface CryptoService {

    fun create(@Valid crypto: Crypto): Crypto

    fun getPrice(cryptoName: CryptoName): Double
}