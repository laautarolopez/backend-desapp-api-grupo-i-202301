package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.QuoteBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class QuoteTest {

    @Autowired
    lateinit var validator: Validator

    val btcusdt : CryptoName = CryptoName.BTCUSDT
    val aaveusdt : CryptoName = CryptoName.AAVEUSDT

    fun anyQuote(): QuoteBuilder {
        return QuoteBuilder()
            .withName(aaveusdt)
            .withPrice(250.00)
            .withTime("2023-07-02T18:19:26.492745400")
    }

    @Test
    fun `a quote is successfully created when it has correct data`() {
        assertDoesNotThrow { anyQuote().build() }
    }

    @Test
    fun `change the name of a quote`() {
        val quote = anyQuote().withName(aaveusdt).build()

        val violations = validator.validate(quote)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the name of a quote for null`() {
        val quote = anyQuote().withName(null).build()

        val violations = validator.validate(quote)

        Assertions.assertTrue(violations.any { v -> v.message == "The crypto name cannot be null." })
    }

    @Test
    fun `change the price of a quote`() {
        val quote = anyQuote().withPrice(250.00).build()

        val violations = validator.validate(quote)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the price of a quote to negative`() {
        val quote = anyQuote().withPrice(-100.0).build()

        val violations = validator.validate(quote)

        Assertions.assertTrue(violations.any { v -> v.message == "The price cannot be negative." })
    }

    @Test
    fun `change the time of a quote`() {
        val quote = anyQuote().withTime("2023-20-15T08:30:26.492745400").build()

        val violations = validator.validate(quote)

        Assertions.assertTrue(violations.isEmpty())
    }
}