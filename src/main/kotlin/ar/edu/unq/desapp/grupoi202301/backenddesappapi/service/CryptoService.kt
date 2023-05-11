package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import jakarta.validation.Valid

interface CryptoService {

    fun create(@Valid crypto: Crypto): Crypto
}