package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.CryptoPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.BinanceResponseInt
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance.PriceResponse
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.exception.CryptoNonExistent
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class CryptoServiceImp(
    @Autowired
    private val cryptoPersistence: CryptoPersistence,
    @Autowired
    private val binanceResponse: BinanceResponseInt
    ) : CryptoService {

    override fun create(crypto: Crypto): Crypto {
        updatePrice(crypto)
        return cryptoPersistence.save(crypto)
    }

    override fun getCrypto(idCrypto: Long?): Crypto {
        validateId(idCrypto)
        try {
            val crypto = cryptoPersistence.getReferenceById(idCrypto!!)
            updatePrice(crypto)
            return crypto
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
        val priceResponse = updatePrice(crypto)
        return priceResponse
    }

    private fun updatePrice(crypto: Crypto): PriceResponse {
        val priceResponse = binanceResponse.getPrice(crypto.name.toString())
        crypto.price = priceResponse.price
        crypto.time = priceResponse.time
        return priceResponse
    }

    private fun update(crypto: Crypto): Crypto {
        getCrypto(crypto.id)
        return cryptoPersistence.save(crypto)
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
        return binanceResponse.getPrices()
    }

    override fun clear() {
        cryptoPersistence.deleteAll()
    }
}