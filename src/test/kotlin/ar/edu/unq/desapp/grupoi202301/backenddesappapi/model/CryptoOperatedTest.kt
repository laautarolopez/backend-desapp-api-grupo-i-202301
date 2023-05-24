package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoOperatedBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CryptoOperatedTest {
    @Autowired
    lateinit var validator: Validator

    var trxusdt : CryptoName = CryptoName.TRXUSDT
    var btcusdt : CryptoName = CryptoName.BTCUSDT

    fun anyCryptoOperated(): CryptoOperatedBuilder {
        return CryptoOperatedBuilder()
            .withCryptoName(trxusdt)
            .withQuantity(60.00)
            .withAmount(150.8)
            .withPrice(200.00)
    }

    @Test
    fun `a cryptoOperated is successfully created when it has correct data`() {
        assertDoesNotThrow { anyCryptoOperated().build() }
    }

    @Test
    fun `change the name of a cryptoOperated`() {
        val cryptoOperated = anyCryptoOperated().withCryptoName(btcusdt).build()

        val violations = validator.validate(cryptoOperated)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the cryptoName of a cryptoOperated for null`() {
        val cryptoOperated = anyCryptoOperated().withCryptoName(null).build()

        val violations = validator.validate(cryptoOperated)

        Assertions.assertTrue(violations.any { v -> v.message == "The crypto name cannot be null." })
    }

    @Test
    fun `change the quantity of a cryptoOperated`() {
        val cryptoOperated = anyCryptoOperated().withQuantity(50.00).build()

        val violations = validator.validate(cryptoOperated)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the quantity of a cryptoOperated to negative`() {
        val cryptoOperated = anyCryptoOperated().withQuantity(-5.0).build()

        val violations = validator.validate(cryptoOperated)

        Assertions.assertTrue(violations.any { v -> v.message == "The quantity cannot be negative." })
    }

    @Test
    fun `a violation occurs when change the quantity of a cryptoOperated for null`() {
        val cryptoOperated = anyCryptoOperated().withQuantity(null).build()

        val violations = validator.validate(cryptoOperated)

        Assertions.assertTrue(violations.any { v -> v.message == "The quantity cannot be null." })
    }

    @Test
    fun `change the amount of a cryptoOperated`() {
        val cryptoOperated = anyCryptoOperated().withAmount(100.00).build()

        val violations = validator.validate(cryptoOperated)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the amount of a cryptoOperated to negative`() {
        val cryptoOperated = anyCryptoOperated().withAmount(-30.0).build()

        val violations = validator.validate(cryptoOperated)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount ARS cannot be negative." })
    }

    @Test
    fun `a violation occurs when change the amount of a cryptoOperated for null`() {
        val cryptoOperated = anyCryptoOperated().withAmount(null).build()

        val violations = validator.validate(cryptoOperated)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount ARS cannot be null." })
    }

    @Test
    fun `change the price of a cryptoOperated`() {
        val cryptoOperated = anyCryptoOperated().withPrice(220.50).build()

        val violations = validator.validate(cryptoOperated)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the price of a cryptoOperated to negative`() {
        val cryptoOperated = anyCryptoOperated().withPrice(-100.0).build()

        val violations = validator.validate(cryptoOperated)

        Assertions.assertTrue(violations.any { v -> v.message == "The price cannot be negative." })
    }

    @Test
    fun `a violation occurs when change the price of a cryptoOperated for null`() {
        val cryptoOperated = anyCryptoOperated().withPrice(null).build()

        val violations = validator.validate(cryptoOperated)

        Assertions.assertTrue(violations.any { v -> v.message == "The price cannot be null." })
    }
}