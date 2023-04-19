package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TradeBuilder
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

    fun anyTrade(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto().build())
            .withQuantity(200.50)
            .withAmountARS(150.8)
            .withUserName("Mariano")
            .withUserLastName("Gomez")
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
    fun `change the username of a trade`() {
        val trade = anyTrade().withUserName("Marcos").build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the name of a user in a trade to null`() {
        val trade = anyTrade().withUserName(null).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The username cannot be null." })
    }

    @Test
    fun `a violation occurs when changing the name of a user with less than 3 characters`() {
        val trade = anyTrade().withUserName("ma").build()

        val violations = validator.validate(trade)

        Assertions.assertEquals(1, violations.size)
        Assertions.assertTrue(violations.any { v -> v.message == "The name must be between 3 and 30 characters long." })
    }

    @Test
    fun `a violation occurs when changing the name of a user with more than 30 characters`() {
        val trade = anyTrade().withUserName("Marcos Alberto Ramon Luis Gerardo").build()

        val violations = validator.validate(trade)

        Assertions.assertEquals(1, violations.size)
        Assertions.assertTrue(violations.any { v -> v.message == "The name must be between 3 and 30 characters long." })
    }

    @Test
    fun `change the user lastname of a trade`() {
        val trade = anyTrade().withUserLastName("Gimenez").build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the lastname of a user in a trade to null`() {
        val trade = anyTrade().withUserLastName(null).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The user lastname cannot be null." })
    }

    @Test
    fun `a violation occurs when changing the lastname of a user with less than 3 characters`() {
        val trade = anyTrade().withUserLastName("as").build()

        val violations = validator.validate(trade)

        Assertions.assertEquals(1, violations.size)
        Assertions.assertTrue(violations.any { v -> v.message == "The last name must be between 3 and 30 characters long." })
    }

    @Test
    fun `a violation occurs when changing the las name of a user with more than 30 characters`() {
        val trade = anyTrade().withUserLastName("Taboada Gomez Lopez Perez Gimenez").build()

        val violations = validator.validate(trade)

        Assertions.assertEquals(1, violations.size)
        Assertions.assertTrue(violations.any { v -> v.message == "The last name must be between 3 and 30 characters long." })
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
}