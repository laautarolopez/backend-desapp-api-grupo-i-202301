package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.QuoteNonExistentException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.QuotePersistence
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
    private val updateQuotesList: UpdateQuotesList
    ): QuoteService {

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

    override fun getQuotesList(): String {
        TODO("Not yet implemented")
    }

    override fun clear() {
        quotesPersistence.deleteAll()
    }
}

@Component
class UpdateQuotesList : Runnable {
    override fun run() {
        while (true) {
            println("-------------------------------")
            println("updateQuotesList")
            println("-------------------------------")
            sleep(2000)
        }
    }
}