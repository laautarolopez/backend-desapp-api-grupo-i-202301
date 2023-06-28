package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.QuoteBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.QuoteServiceImp
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class QuoteServiceTest {
    @Autowired
    lateinit var quoteService: QuoteServiceImp

    val aaveusdt : CryptoName = CryptoName.AAVEUSDT
    val btcusdt : CryptoName = CryptoName.BTCUSDT
    val ethusdt : CryptoName = CryptoName.ETHUSDT

    fun anyQuote(): QuoteBuilder {
        return QuoteBuilder()
            .withName(aaveusdt)
            .withPrice(250.00)
            .withTime("2023-07-02T18:19:26.492745400")
    }

    fun otherQuote1(): QuoteBuilder {
        return QuoteBuilder()
            .withName(aaveusdt)
            .withPrice(250.00)
            .withTime("2023-07-02T18:19:26.492745400")
    }

    fun otherQuote2(): QuoteBuilder {
        return QuoteBuilder()
            .withName(ethusdt)
            .withPrice(250.00)
            .withTime("2023-07-02T18:19:26.492745400")
    }

    fun otherQuote3(): QuoteBuilder {
        return QuoteBuilder()
            .withName(btcusdt)
            .withPrice(250.00)
            .withTime("2023-07-02T18:19:26.492745400")
    }

    @Test
    fun `a quote is successfully created when it has correct data`() {
        val anyQuote = anyQuote().build()

        val quote = quoteService.create(anyQuote)

        Assertions.assertTrue(quote.id != null)
    }

    @Test
    fun `change the name of a quote`() {
        val quoteRequested = anyQuote().withName(aaveusdt).build()

        val quote = quoteService.create(quoteRequested)

        Assertions.assertTrue(quote.id != null)
    }

    @Test
    fun `a violation occurs when change the name of a quote for null`() {
        val quoteRequested = anyQuote().withName(null).build()

        try {
            quoteService.create(quoteRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.quote.cryptoName: The crypto name cannot be null.", e.message)
        }
    }

    @Test
    fun `a quote is successfully getted`() {
        val anyQuote = anyQuote().build()

        val quote = quoteService.create(anyQuote)
        val idQuote = quote.id

        val quoteGetted = quoteService.getQuote(idQuote!!)

        Assertions.assertEquals(quote, quoteGetted)
    }

    @Test
    fun `an exception be thrown when a quote is not exist`() {
        try {
            quoteService.getQuote(55)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The quote does not exist.", e.message)
        }
    }

    @Test
    fun `4 quotes are recovered successfully`() {
        quoteService.create(anyQuote().build())
        quoteService.create(otherQuote1().build())
        quoteService.create(otherQuote2().build())
        quoteService.create(otherQuote3().build())

        var quotes = quoteService.recoverAll()

        println(quotes.size)
        Assertions.assertTrue(quotes.size == 4)
    }

    @AfterEach
    fun clear() {
        quoteService.clear()
    }
}