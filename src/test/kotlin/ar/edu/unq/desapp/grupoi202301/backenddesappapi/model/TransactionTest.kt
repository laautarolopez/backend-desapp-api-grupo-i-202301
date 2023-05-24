package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TradeBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.UserBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class TransactionTest {
    @Autowired
    lateinit var validator: Validator

    var aaveusdt : CryptoName = CryptoName.AAVEUSDT
    var trxusdt : CryptoName = CryptoName.TRXUSDT
    var confirm : ActionTransaction = ActionTransaction.CONFIRM
    var make : ActionTransaction = ActionTransaction.MAKE
    var sale : OperationType = OperationType.SALE

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

    fun anyCrypto(): CryptoBuilder {
        return CryptoBuilder()
            .withName(CryptoName.BTCUSDT)
    }

    val anyTrade: Trade =
            TradeBuilder()
            .withCrypto(anyCrypto().build())
            .withQuantity(200.50)
            .withUser(anyUser)
            .withOperation(sale).build()

    fun anyTransaction(): TransactionBuilder {
        return TransactionBuilder()
            .withAmountOperation(200.4)
            .withTrade(anyTrade)
            .withShippingAddress("1234567890123456789012")
            .withAction(confirm)
    }

    @Test
    fun `a transaction is successfully created when it has correct data`() {
        assertDoesNotThrow { anyTransaction().build() }
    }

    @Test
    fun `change the id user requested`() {
        val transaction = anyTransaction().withIdUserRequested(3).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the id user requested to null`() {
        val transaction = anyTransaction().withIdUserRequested(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The idUserRequested cannot be null." })
    }

    @Test
    fun `change the amount operation of a transaction`() {
        val transaction = anyTransaction().withAmountOperation(55.86).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the amount operation of a transaction to negative`() {
        val transaction = anyTransaction().withAmountOperation(-15.00).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount of operation cannot be negative." })
    }


    @Test
    fun `a violation occurs when change the amount operation of a transaction for null`() {
        val transaction = anyTransaction().withAmountOperation(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount of operation cannot be null." })
    }

    @Test
    fun `change the trade of a transaction`() {
        val otherTrade: Trade =
            TradeBuilder()
                .withCrypto(anyCrypto().build())
                .withQuantity(150.0)
                .withUser(anyUser)
                .withOperation(sale).build()

        val transaction = anyTransaction().withTrade(otherTrade).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the trade a transaction to null`() {
        val transaction = anyTransaction().withTrade(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The trade cannot be null." })
    }

    @Test
    fun `change the shipping address of a transaction`() {
        val transaction = anyTransaction().withShippingAddress("2131267169283121567927").build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the shipping address in a transaction to null`() {
        val transaction = anyTransaction().withShippingAddress(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The shipping address cannot be null." })
    }

    @Test
    fun `change the action of a transaction`() {
        val transaction = anyTransaction().withAction(make).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `change the status of a transaction`() {

        val transaction = anyTransaction().withStatus(statusCanceled).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The action cannot be null." })
    }
}