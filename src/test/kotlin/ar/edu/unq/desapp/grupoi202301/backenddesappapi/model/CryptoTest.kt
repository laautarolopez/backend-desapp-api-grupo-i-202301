package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.Quote24hsBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CryptoTest {
    @Autowired
    lateinit var validator: Validator

    val btcusdt : CryptoName = CryptoName.BTCUSDT
    val aaveusdt : CryptoName = CryptoName.AAVEUSDT

    fun anyCrypto(): CryptoBuilder {
        return CryptoBuilder()
            .withName(btcusdt)
            .withPrice(100.00)
    }

    fun anyQuote24hs(): Quote24hsBuilder {
        return Quote24hsBuilder()
            .withName(aaveusdt)
            .withPrice(250.00)
            .withTime("2023-07-02T18:19:26.492745400")
    }

    @Test
    fun `a crypto is successfully created when it has correct data`() {
        assertDoesNotThrow { anyCrypto().build() }
    }

    @Test
    fun `change the name of a crypto`() {
        val crypto = anyCrypto().withName(aaveusdt).build()

        val violations = validator.validate(crypto)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the name of a crypto for null`() {
        val crypto = anyCrypto().withName(null).build()

        val violations = validator.validate(crypto)

        Assertions.assertTrue(violations.any { v -> v.message == "The crypto name cannot be null." })
    }

    @Test
    fun `change the price of a crypto`() {
        val crypto = anyCrypto().withPrice(250.00).build()

        val violations = validator.validate(crypto)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the price of a crypto to negative`() {
        val crypto = anyCrypto().withPrice(-100.0).build()

        val violations = validator.validate(crypto)

        Assertions.assertTrue(violations.any { v -> v.message == "The price cannot be negative." })
    }

    @Test
    fun `a quote is added to Crypto collection`() {
        val crypto = anyCrypto().build()
        val quote24hs = anyQuote24hs().build()

        Assertions.assertTrue(crypto.quotes24hs.isEmpty())

        crypto.addQuote(quote24hs)

        Assertions.assertFalse(crypto.quotes24hs.isEmpty())
        Assertions.assertTrue(crypto.quotes24hs.size == 1)
    }

    @Test
    fun `a quote is remove to Crypto collection`() {
        val crypto = anyCrypto().build()
        val quote24hs = anyQuote24hs().build()

        crypto.addQuote(quote24hs)

        Assertions.assertFalse(crypto.quotes24hs.isEmpty())
        Assertions.assertTrue(crypto.quotes24hs.size == 1)

        crypto.removeQuote(quote24hs)

        Assertions.assertTrue(crypto.quotes24hs.isEmpty())
    }
}