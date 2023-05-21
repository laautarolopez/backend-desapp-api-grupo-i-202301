package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.CryptoPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.BinanceResponse
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.PriceResponse
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class CryptoServiceImp(
    private val cryptoPersistence: CryptoPersistence
    ) : CryptoService {

    override fun create(crypto: Crypto): Crypto {
        return cryptoPersistence.save(crypto)
    }

    override fun update(crypto: Crypto): Crypto {
        return this.create(crypto)
    }

    override fun recove(cryptoId: Int): Crypto {
        val crypto = cryptoPersistence.findByIdOrNull(cryptoId.toLong())
        if (crypto == null) {
            throw RuntimeException("The id does not exist.")
        }
        return crypto
    }

    override fun getPrice(cryptoName: String): PriceResponse {
        return BinanceResponse().getPrice(cryptoName)
    }

    override fun getPrices(): List<PriceResponse> {
        return BinanceResponse().getPrices()
    }

    override fun clear() {
        cryptoPersistence.deleteAll()
    }
}