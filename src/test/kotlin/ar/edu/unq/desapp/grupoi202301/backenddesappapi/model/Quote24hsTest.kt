package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.Quote24hsBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.QuoteBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class Quote24hsTest {
    @Autowired
    lateinit var validator: Validator

    val btcusdt : CryptoName = CryptoName.BTCUSDT
    val aaveusdt : CryptoName = CryptoName.AAVEUSDT

    fun anyQuote24hs(): Quote24hsBuilder {
        return Quote24hsBuilder()
            .withName(aaveusdt)
            .withPrice(250.00)
            .withTime("2023-07-02T18:19:26.492745400")
    }

    @Test
    fun `a quote24hs is successfully created when it has correct data`() {
        assertDoesNotThrow { anyQuote24hs().build() }
    }

    @Test
    fun `change the name of a quote24hs`() {
        val quote24hs = anyQuote24hs().withName(aaveusdt).build()

        val violations = validator.validate(quote24hs)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the name of a quote24hs for null`() {
        val quote24hs = anyQuote24hs().withName(null).build()

        val violations = validator.validate(quote24hs)

        Assertions.assertTrue(violations.any { v -> v.message == "The crypto name cannot be null." })
    }

    @Test
    fun `change the price of a quote24hs`() {
        val quote24hs = anyQuote24hs().withPrice(250.00).build()

        val violations = validator.validate(quote24hs)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the price of a quote24hs to negative`() {
        val quote24hs = anyQuote24hs().withPrice(-100.0).build()

        val violations = validator.validate(quote24hs)

        Assertions.assertTrue(violations.any { v -> v.message == "The price cannot be negative." })
    }

    @Test
    fun `change the time of a quote24hs`() {
        val quote24hs = anyQuote24hs().withTime("2023-20-15T08:30:26.492745400").build()

        val violations = validator.validate(quote24hs)

        Assertions.assertTrue(violations.isEmpty())
    }
}