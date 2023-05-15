package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoVolume
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.CryptoVolumePersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoVolumeService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class CryptoVolumeServiceImp(
    private val cryptoVolumePersistence: CryptoVolumePersistence
) : CryptoVolumeService {

    override fun create(crypto: CryptoVolume): CryptoVolume {
        return cryptoVolumePersistence.save(crypto)
    }
}