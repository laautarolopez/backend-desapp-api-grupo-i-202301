package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoVolume
import jakarta.validation.Valid

interface CryptoVolumeService {

    fun create(@Valid crypto: CryptoVolume): CryptoVolume
}