package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TradeBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.UserBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class TradeTest {
    @Autowired
    lateinit var validator: Validator

    var sale : OperationType = OperationType.SALE
    var buy : OperationType = OperationType.BUY

    fun anyCrypto(): CryptoBuilder {
        return CryptoBuilder()
            .withName(CryptoName.BTCUSDT)
            .withTime(LocalDateTime.now())
            .withPrice(300.50)
    }

    val anyUser: User =
        UserBuilder()
            .withName("Jorge")
            .withLastName("Sanchez")
            .withEmail("jorgesanchez@gmail.com")
            .withAddress("calle falsa 123")
            .withPassword("Password@1234")
            .withCVU("1234567890123456789012")
            .withWalletAddress("12345678")
            .build()

    fun anyTrade(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto().build())
            .withQuantity(200.50)
            .withAmountARS(150.8)
            .withUser(anyUser)
            .withOperation(sale)
    }

    @Test
    fun `a trade is successfully created when it has correct data`() {
        assertDoesNotThrow { anyTrade().build() }
    }

    @Test
    fun `change the crypto of a trade`() {
        val anyCrypto = anyCrypto().withPrice(10.0).build()

        val trade = anyTrade().withCrypto(anyCrypto).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the crypto of a trade for null`() {
        val trade = anyTrade().withCrypto(null).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The crypto cannot be null." })
    }

    @Test
    fun `change the quantity of a trade`() {
        val trade = anyTrade().withQuantity(250.20).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the quantity of a trade to negative`() {
        val trade = anyTrade().withQuantity(-100.0).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The quantity cannot be negative." })
    }

    @Test
    fun `a violation occurs when change the quantity of a trade for null`() {
        val trade = anyTrade().withQuantity(null).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The quantity cannot be null." })
    }

    @Test
    fun `change the amountARS of a trade`() {
        val trade = anyTrade().withAmountARS(220.80).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the amountARS of a trade for null`() {
        val trade = anyTrade().withAmountARS(null).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount cannot be null." })
    }

    @Test
    fun `a violation occurs when change the amountARS of a trade to negative`() {
        val trade = anyTrade().withAmountARS(-10.0).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount cannot be negative." })
    }

    @Test
    fun `change the user of a trade`() {
        val otherUser: User =
            UserBuilder()
                .withName("Jimena")
                .withLastName("Lopez")
                .withEmail("jimenalopez@gmail.com")
                .withAddress("calle 43")
                .withPassword("Password@4444")
                .withCVU("4321567890123456781112")
                .withWalletAddress("87651234")
                .build()

        val trade = anyTrade().withUser(otherUser).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the user in a trade to null`() {
        val trade = anyTrade().withUser(null).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The user cannot be null." })
    }

    @Test
    fun `change the operation of a trade`() {
        val trade = anyTrade().withOperation(buy).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the operation of a trade for null`() {
        val trade = anyTrade().withOperation(null).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The operation cannot be null." })
    }

    @Test
    fun `change the isActive of a trade`() {
        val trade = anyTrade().withIsActive(false).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }
}