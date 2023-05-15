package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoOperated
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.CryptoOperatedPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoOperatedService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class CryptoOperatedServiceImp(
    private val cryptoOperatedPersistence: CryptoOperatedPersistence
) : CryptoOperatedService {

    override fun create(crypto: CryptoOperated): CryptoOperated {
        return cryptoOperatedPersistence.save(crypto)
    }
}