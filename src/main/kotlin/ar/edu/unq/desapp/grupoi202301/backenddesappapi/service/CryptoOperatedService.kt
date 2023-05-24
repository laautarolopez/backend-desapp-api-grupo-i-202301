package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoOperated
import jakarta.validation.Valid

interface CryptoOperatedService {

    fun create(@Valid cryptoOperated: CryptoOperated): CryptoOperated
}