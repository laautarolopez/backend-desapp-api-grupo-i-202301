package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TradeBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TradeTest {
    @Autowired
    lateinit var validator: Validator

    var crypto1 : Crypto = Crypto()
    var crypto2 : Crypto = Crypto()
    var sale : OperationType = OperationType.SALE
    var buy : OperationType = OperationType.BUY

    fun anyTrade(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(crypto1)
            .withQuantity(200.50)
            .withAmountARS(150.8)
            .withUser("Mariano")
            .withOperation(sale)
    }

    @Test
    fun `a trade is successfully created when it has correct data`() {
        assertDoesNotThrow { anyTrade().build() }
    }

    @Test
    fun `change the crypto of an trade`() {
        val trade = anyTrade().withCrypto(crypto2).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation to change the crypto of a trade for something empty`() {
        val trade = anyTrade().withCrypto(null).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The crypto cannot be null." })
    }

    @Test
    fun `change the quantity of an trade`() {
        val trade = anyTrade().withQuantity(250.20).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation to change the quantity of a trade for something empty`() {
        val trade = anyTrade().withQuantity(null).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The quantity cannot be null." })
    }

    @Test
    fun `change the amountARS of an trade`() {
        val trade = anyTrade().withAmountARS(220.80).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation to change the amountARS of a trade for something empty`() {
        val trade = anyTrade().withAmountARS(null).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount cannot be null." })
    }

    @Test
    fun `change the name of user of an trade`() {
        val trade = anyTrade().withUser("Marcos").build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the name of a user in a trade to empty`() {
        val trade = anyTrade().withUser(null).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The user cannot be null." })
    }

    @Test
    fun `a violation occurs when changing the name of a user with less than 3 characters`() {
        val trade = anyTrade().withUser("ma").build()

        val violations = validator.validate(trade)

        Assertions.assertEquals(1, violations.size)
        Assertions.assertTrue(violations.any { v -> v.message == "The name must be between 3 and 30 characters long." })
    }

    @Test
    fun `a violation occurs when changing the name of a user with more than 30 characters`() {
        val trade = anyTrade().withUser("Marcos Alberto Ramon Luis Gerardo").build()

        val violations = validator.validate(trade)

        Assertions.assertEquals(1, violations.size)
        Assertions.assertTrue(violations.any { v -> v.message == "The name must be between 3 and 30 characters long." })
    }

    @Test
    fun `change the operation of an trade`() {
        val trade = anyTrade().withOperation(buy).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation to change the operation of a trade for something empty`() {
        val trade = anyTrade().withOperation(null).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The operation cannot be null." })
    }
}