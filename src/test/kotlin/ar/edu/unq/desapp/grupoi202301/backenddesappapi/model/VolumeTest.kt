package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.VolumeBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class VolumeTest {
    @Autowired
    lateinit var validator: Validator

    fun anyVolumen(): VolumeBuilder {
        return VolumeBuilder()
            .withIdUser(1)
            .withDate(LocalDateTime.now())
            .withAmountUSD(150.8)
            .withAmountARS(60.00)
            .withCryptos(listOf())
    }

    @Test
    fun `a volumen is successfully created when it has correct data`() {
        assertDoesNotThrow { anyVolumen().build() }
    }

    @Test
    fun `change the idUser of a volumen`() {
        val volumen = anyVolumen().withIdUser(2).build()

        val violations = validator.validate(volumen)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the idUser of a volumen for null`() {
        val volumen = anyVolumen().withIdUser(null).build()

        val violations = validator.validate(volumen)

        Assertions.assertTrue(violations.any { v -> v.message == "The idUser cannot be null." })
    }

    @Test
    fun `change the time request of a cryptoVolume`() {
        val oldTime = anyVolumen().date
        val volumen = anyVolumen().withDate(LocalDateTime.now()).build()

        val violations = validator.validate(volumen)

        Assertions.assertTrue(violations.isEmpty())
        Assertions.assertTrue(oldTime != volumen.date)
    }

    @Test
    fun `a violation occurs when change the time request of a volumen for null`() {
        val volumen = anyVolumen().withDate(null).build()

        val violations = validator.validate(volumen)

        Assertions.assertTrue(violations.any { v -> v.message == "The date cannot be null." })
    }

    @Test
    fun `change the amount USD of a volumen`() {
        val volumen = anyVolumen().withAmountUSD(220.14).build()

        val violations = validator.validate(volumen)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the amount USD of a volumen to negative`() {
        val volumen = anyVolumen().withAmountUSD(-60.0).build()

        val violations = validator.validate(volumen)

        Assertions.assertTrue(violations.any { v -> v.message == "The amountUSD cannot be negative." })
    }

    @Test
    fun `a violation occurs when change the amount USD of a volumen for null`() {
        val volumen = anyVolumen().withAmountUSD(null).build()

        val violations = validator.validate(volumen)

        Assertions.assertTrue(violations.any { v -> v.message == "The amountUSD cannot be null." })
    }

    @Test
    fun `change the amount ARS of a volumen`() {
        val volumen = anyVolumen().withAmountARS(150.45).build()

        val violations = validator.validate(volumen)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the amount ARS of a volumen to negative`() {
        val volumen = anyVolumen().withAmountARS(-90.0).build()

        val violations = validator.validate(volumen)

        Assertions.assertTrue(violations.any { v -> v.message == "The amountARS cannot be negative." })
    }

    @Test
    fun `a violation occurs when change the amount ARS of a volumen for null`() {
        val volumen = anyVolumen().withAmountARS(null).build()

        val violations = validator.validate(volumen)

        Assertions.assertTrue(violations.any { v -> v.message == "The amountARS cannot be null." })
    }
}