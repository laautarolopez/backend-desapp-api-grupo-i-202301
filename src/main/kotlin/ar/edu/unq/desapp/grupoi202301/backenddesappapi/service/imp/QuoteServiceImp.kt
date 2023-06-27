package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote24hs
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.QuoteNonExistentException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.QuotePersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.PriceResponse
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.Quote24hsService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.QuoteService
import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.lang.Thread.sleep

@Service
@Validated
@Transactional
class QuoteServiceImp(
    @Autowired
    private val quotesPersistence: QuotePersistence,
    @Autowired
    private val cryptoService: CryptoService,
    @Autowired
    private val quote24hsService: Quote24hsService
    ): QuoteService {

    private val updateQuotesList: UpdateQuotesListServiceImp = UpdateQuotesListServiceImp(this, cryptoService, quote24hsService)

    @PostConstruct
    override fun updateQuotesList() {
        val thread = Thread(updateQuotesList)
        thread.start()
    }

    override fun create(quote: Quote): Quote {
        return quotesPersistence.save(quote)
    }

    override fun update(quote: Quote): Quote {
        getQuote(quote.id)
        return quotesPersistence.save(quote)
    }

    override fun getQuote(idQuote: Long?): Quote {
        validateId(idQuote)
        try {
            val quote = quotesPersistence.getReferenceById(idQuote!!)
            return quote
        } catch(e: RuntimeException) {
            throw QuoteNonExistentException("quote", "The quote does not exist.")
        }
    }

    private fun validateId(idQuote: Long?) {
        if(idQuote == null) {
            throw QuoteNonExistentException("quote", "The quote does not exist.")
        }
    }

    override fun findByCryptoName(cryptoName: CryptoName): Quote {
        return quotesPersistence.findByCryptoName(cryptoName)
    }

    override fun getQuotesList(): List<Quote> {
        return quotesPersistence.findAll()
    }

    override fun clear() {
        quotesPersistence.deleteAll()
    }
}

@Component
class UpdateQuotesListServiceImp(
    private val quoteService: QuoteService,
    private val cryptoService: CryptoService,
    private val quote24hsService: Quote24hsService
    ) : Runnable {

    override fun run() {
        var prices = cryptoService.getPrices()
        createQuotes(prices, quoteService)
        createQuotes24hs(prices, quote24hsService, cryptoService)
        while (true) {
            sleep(600000)
            prices = cryptoService.getPrices()
            updateQuotes(prices, quoteService)
        }
    }

    private fun createQuotes(prices: List<PriceResponse>, quoteService: QuoteService) {
        prices.forEach {
            priceResponse -> val newQuote = Quote()
            newQuote.cryptoName = enumValueOf<CryptoName>(priceResponse.cryptoName)
            newQuote.price = priceResponse.price
            newQuote.time = priceResponse.time
            quoteService.create(newQuote)
        }
    }

    private fun createQuotes24hs(prices: List<PriceResponse>, quote24hsService: Quote24hsService, cryptoService: CryptoService) {
        prices.forEach {
                priceResponse -> val newQuote24hs = Quote24hs()
            newQuote24hs.cryptoName = enumValueOf<CryptoName>(priceResponse.cryptoName)
            newQuote24hs.price = priceResponse.price
            newQuote24hs.time = priceResponse.time
            quote24hsService.create(newQuote24hs)
            val crypto = cryptoService.findByName(newQuote24hs.cryptoName!!)
            crypto.quotes24hs = mutableListOf(newQuote24hs)
            cryptoService.update(crypto)
            println(crypto.name)
            println(crypto.quotes24hs.size)
        }
    }

    private fun updateQuotes(prices: List<PriceResponse>, quoteService: QuoteService) {
        prices.forEach {
            priceResponse -> val cryptoName = enumValueOf<CryptoName>(priceResponse.cryptoName)
            try {
                val quote = quoteService.findByCryptoName(cryptoName)
                quote.price = priceResponse.price
                quote.time = priceResponse.time
                quoteService.update(quote)
            } catch(e: RuntimeException) {
                throw QuoteNonExistentException("quote", "The quote does not exist.")
            }
        }
    }
}