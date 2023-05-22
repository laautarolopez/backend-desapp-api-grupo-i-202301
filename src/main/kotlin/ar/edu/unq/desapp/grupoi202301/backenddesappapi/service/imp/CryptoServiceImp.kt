package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.CryptoPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.BinanceResponse
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.PriceResponse
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.exception.CryptoNonExistent
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime

@Service
@Validated
@Transactional
class CryptoServiceImp(
    private val cryptoPersistence: CryptoPersistence
    ) : CryptoService {

    override fun create(crypto: Crypto): Crypto {
        return cryptoPersistence.save(crypto)
    }

    override fun getCrypto(idCrypto: Long): Crypto {
        try {
            val crypto = cryptoPersistence.getReferenceById(idCrypto)
            return updatePrice(crypto)
        } catch(e: RuntimeException) {
            throw CryptoNonExistent()
        }
    }

    private fun updatePrice(crypto: Crypto): Crypto {
        val price = this.getPrice(crypto.name.toString()).price
        val time = LocalDateTime.now()
        crypto.price = price
        crypto.time = time
        return cryptoPersistence.save(crypto)
    }

    override fun getPrice(cryptoName: String): PriceResponse {
        return BinanceResponse().getPrice(cryptoName)
        // TODO: Validar
    }

    override fun getPrices(): List<PriceResponse> {
        return BinanceResponse().getPrices()
    }

    override fun clear() {
        cryptoPersistence.deleteAll()
    }
}