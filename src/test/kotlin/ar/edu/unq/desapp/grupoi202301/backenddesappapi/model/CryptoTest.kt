package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
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
}