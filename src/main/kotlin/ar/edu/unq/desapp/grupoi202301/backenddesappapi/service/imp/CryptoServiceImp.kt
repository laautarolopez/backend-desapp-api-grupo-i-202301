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

@Service
@Validated
@Transactional
class CryptoServiceImp(
    private val cryptoPersistence: CryptoPersistence
    ) : CryptoService {

    override fun create(crypto: Crypto): Crypto {
        return cryptoPersistence.save(crypto)
    }

    override fun getCrypto(idCrypto: Long?): Crypto {
        validateId(idCrypto)
        try {
            return cryptoPersistence.getReferenceById(idCrypto!!)
        } catch(e: RuntimeException) {
            throw CryptoNonExistent()
        }
    }

    private fun validateId(idCrypto: Long?) {
        if(idCrypto == null) {
            throw CryptoNonExistent()
        }
    }

    override fun getPrice(cryptoName: String): PriceResponse {
        val crypto = this.getCryptoByName(cryptoName)
        return crypto.getPrice()
    }

    private fun getCryptoByName(cryptoName: String): Crypto {
        try {
            val cryptos = cryptoPersistence.findAll()
            val crypto = cryptos.find {
                crypto -> crypto.name.toString() == cryptoName
            }
            return crypto!!
        } catch(e: RuntimeException) {
            throw CryptoNonExistent()
        }
    }

    override fun getPrices(): List<PriceResponse> {
        return BinanceResponse().getPrices()
    }

    override fun clear() {
        cryptoPersistence.deleteAll()
    }
}