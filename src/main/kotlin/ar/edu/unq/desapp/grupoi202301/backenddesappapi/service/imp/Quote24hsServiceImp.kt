package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote24hs
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.QuoteNonExistentException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.Quote24hsPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.Quote24hsService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class Quote24hsServiceImp(
    @Autowired
    private val quote24hsPersistence: Quote24hsPersistence
    ) : Quote24hsService{

    override fun create(quote24hs: Quote24hs): Quote24hs {
        return quote24hsPersistence.save(quote24hs)
    }

    override fun update(quote24hs: Quote24hs): Quote24hs {
        getQuote24hs(quote24hs.id)
        return quote24hsPersistence.save(quote24hs)
    }

    override fun delete(quote24hs: Quote24hs) {
        quote24hsPersistence.delete(quote24hs)
    }

    override fun findByCryptoName(cryptoName: CryptoName): List<Quote24hs> {
        return quote24hsPersistence.findByCryptoName(cryptoName)
    }

    override fun getQuote24hs(idQuote24hs: Long?): Quote24hs {
        validateId(idQuote24hs)
        try {
            val quote24hs = quote24hsPersistence.getReferenceById(idQuote24hs!!)
            return quote24hs
        } catch(e: RuntimeException) {
            throw QuoteNonExistentException("quote", "The quote does not exist.")
        }
    }

    private fun validateId(idQuote24hs: Long?) {
        if(idQuote24hs == null) {
            throw QuoteNonExistentException("quote", "The quote does not exist.")
        }
    }

    override fun recoverAll(): List<Quote24hs> {
        return quote24hsPersistence.findAll()
    }

    override fun clear() {
        quote24hsPersistence.deleteAll()
    }
}