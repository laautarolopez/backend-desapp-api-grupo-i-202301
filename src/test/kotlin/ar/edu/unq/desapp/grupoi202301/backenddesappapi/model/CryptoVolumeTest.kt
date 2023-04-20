package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoVolumeBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class CryptoVolumeTest {
    @Autowired
    lateinit var validator: Validator

    fun anyCryptoVolume(): CryptoVolumeBuilder {
        return CryptoVolumeBuilder()
            .withTimeRequest(LocalDateTime.now())
            .withAmountARS(60.00)
            .withAmountUSD(150.8)
            .withCryptos(listOf())
    }

    @Test
    fun `a cryptoVolume is successfully created when it has correct data`() {
        assertDoesNotThrow { anyCryptoVolume().build() }
    }

    @Test
    fun `change the time request of a cryptoVolume`() {
        val oldTime = anyCryptoVolume().timeRequest
        val cryptoVolume = anyCryptoVolume().withTimeRequest(LocalDateTime.now()).build()

        val violations = validator.validate(cryptoVolume)

        Assertions.assertTrue(violations.isEmpty())
        Assertions.assertTrue(oldTime != cryptoVolume.timeRequest)
    }

    @Test
    fun `a violation occurs when change the time request of a cryptoVolume for null`() {
        val cryptoVolume = anyCryptoVolume().withTimeRequest(null).build()

        val violations = validator.validate(cryptoVolume)

        Assertions.assertTrue(violations.any { v -> v.message == "The time request cannot be null." })
    }

    @Test
    fun `change the amount USD of a cryptoVolume`() {
        val cryptoVolume = anyCryptoVolume().withAmountUSD(220.14).build()

        val violations = validator.validate(cryptoVolume)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the amount USD of a cryptoVolume to negative`() {
        val cryptoVolume = anyCryptoVolume().withAmountUSD(-60.0).build()

        val violations = validator.validate(cryptoVolume)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount USD cannot be negative." })
    }

    @Test
    fun `a violation occurs when change the amount USD of a cryptoVolume for null`() {
        val cryptoVolume = anyCryptoVolume().withAmountUSD(null).build()

        val violations = validator.validate(cryptoVolume)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount USD cannot be null." })
    }

    @Test
    fun `change the amount ARS of a cryptoVolume`() {
        val cryptoVolume = anyCryptoVolume().withAmountARS(150.45).build()

        val violations = validator.validate(cryptoVolume)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the amount ARS of a cryptoVolume to negative`() {
        val cryptoVolume = anyCryptoVolume().withAmountARS(-90.0).build()

        val violations = validator.validate(cryptoVolume)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount ARS cannot be negative." })
    }

    @Test
    fun `a violation occurs when change the amount ARS of a cryptoVolume for null`() {
        val cryptoVolume = anyCryptoVolume().withAmountARS(null).build()

        val violations = validator.validate(cryptoVolume)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount ARS cannot be null." })
    }
}