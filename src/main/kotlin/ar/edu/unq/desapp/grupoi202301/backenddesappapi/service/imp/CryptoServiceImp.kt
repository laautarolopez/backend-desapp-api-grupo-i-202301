package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote24hs
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.CryptoPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.BinanceResponseInt
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.PriceResponse
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.Quote24hsService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.exception.CryptoNonExistent
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
@Validated
@Transactional
class CryptoServiceImp(
    @Autowired
    private val cryptoPersistence: CryptoPersistence,
    @Autowired
    private val binanceResponse: BinanceResponseInt,
    @Autowired
    private val quote24hsService: Quote24hsService
    ) : CryptoService {

    override fun create(crypto: Crypto): Crypto {
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

    override fun update(crypto: Crypto): Crypto {
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

    @Cacheable("cryptoGetPrices")
    override fun getPrices(): List<PriceResponse> {
        return binanceResponse.getPrices()
    }

    override fun getQuotes24hs(cryptoName: String): List<Quote24hs> {
        val crypto = getCryptoByName(cryptoName)
        return crypto.quotes24hs
    }

    override fun findByName(name: CryptoName): Crypto {
        return cryptoPersistence.findByName(name)
    }

    override fun updateQuotes24hs(crypto: Crypto) {
        val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val timeNow = LocalDateTime.now()
        val timeAfter24hs = timeNow.minusHours(24)

        crypto.quotes24hs.forEach{ quote24hs ->
            val quoteTime = LocalDateTime.parse(quote24hs.time, formatter)
            if(!between(quoteTime, timeAfter24hs, timeNow)) {
                crypto.removeQuote(quote24hs)
                quote24hsService.delete(quote24hs)
            }
        }
    }

    private fun <LocalDateTime : Comparable<LocalDateTime>> between(dateQuote: LocalDateTime, firstDate: LocalDateTime, lastDate: LocalDateTime): Boolean {
        return dateQuote in firstDate..lastDate
    }

    override fun clear() {
        cryptoPersistence.deleteAll()
    }
}