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
            .withTime(LocalDateTime.now())
            .withPrice(300.50)
    }

    val anyTrade: Trade =
            TradeBuilder()
            .withCrypto(anyCrypto().build())
            .withQuantity(200.50)
            .withAmountARS(150.8)
            .withUser(anyUser)
            .withOperation(sale).build()

    fun anyTransaction(): TransactionBuilder {
        return TransactionBuilder()
            .withCrypto(aaveusdt)
            .withQuantity(300.10)
            .withQuotationCrypto(15.4)
            .withAmountOperation(200.4)
            .withUser(anyUser)
            .withNumberOperations(5)
            .withReputation(7)
            .withTrade(anyTrade)
            .withShippingAddress("1234567890123456789012")
            .withAction(confirm)
    }

    @Test
    fun `a transaction is successfully created when it has correct data`() {
        assertDoesNotThrow { anyTransaction().build() }
    }

    @Test
    fun `change the cryptoname of a transaction`() {
        val transaction = anyTransaction().withCrypto(trxusdt).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the cryptoname of a transaction for null`() {
        val crypto = anyTransaction().withCrypto(null).build()

        val violations = validator.validate(crypto)

        Assertions.assertTrue(violations.any { v -> v.message == "The crypto cannot be null." })
    }

    @Test
    fun `change the quantity of a transaction`() {
        val transaction = anyTransaction().withQuantity(250.06).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the quantity of a transaction to negative`() {
        val trade = anyTransaction().withQuantity(-20.0).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The quantity cannot be negative." })
    }

    @Test
    fun `a violation occurs when change the quantity of a transaction for null`() {
        val transaction = anyTransaction().withQuantity(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The quantity cannot be null." })
    }

    @Test
    fun `change the quotation crypto of a transaction`() {
        val transaction = anyTransaction().withQuotationCrypto(150.86).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the quotation crypto of a transaction to negative`() {
        val trade = anyTransaction().withQuotationCrypto(-30.0).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The quotation crypto cannot be negative." })
    }

    @Test
    fun `a violation occurs when change the quotation crypto of a transaction for null`() {
        val transaction = anyTransaction().withQuotationCrypto(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The quotation crypto cannot be null." })
    }

    @Test
    fun `change the amount operation of a transaction`() {
        val transaction = anyTransaction().withAmountOperation(55.86).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when change the amount operation of a transaction to negative`() {
        val trade = anyTransaction().withAmountOperation(-15.00).build()

        val violations = validator.validate(trade)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount of operation cannot be negative." })
    }


    @Test
    fun `a violation occurs when change the amount operation of a transaction for null`() {
        val transaction = anyTransaction().withAmountOperation(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount of operation cannot be null." })
    }

    @Test
    fun `change the user of a transaction`() {
        val otherUser: User =
            UserBuilder()
                .withName("Matheo")
                .withLastName("Gomez")
                .withEmail("matheogomez@gmail.com")
                .withAddress("calle falsa 321")
                .withPassword("Password@4321")
                .withCVU("4321567890123456789012")
                .withWalletAddress("56781234")
                .build()

        val transaction = anyTransaction().withUser(otherUser).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the user in a transaction to null`() {
        val transaction = anyTransaction().withUser(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The user cannot be null." })
    }

    @Test
    fun `change the number operations of a transaction`() {
        val transaction = anyTransaction().withNumberOperations(25).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when the number of operations in a transaction is changed to a negative`() {
        val transaction = anyTransaction().withNumberOperations(-1).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The number must be equal to or greater than 0" })
    }

    @Test
    fun `a violation occurs when changing the number of operations in a transaction to null`() {
        val transaction = anyTransaction().withNumberOperations(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The numbers of operations cannot be null." })
    }

    @Test
    fun `change the reputation of a transaction`() {
        val transaction = anyTransaction().withReputation(3).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when the reputation in a transaction is changed to a negative`() {
        val transaction = anyTransaction().withReputation(-5).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The number must be equal to or greater than 0" })
    }

    @Test
    fun `a violation occurs when changing the reputation in a transaction to null`() {
        val transaction = anyTransaction().withReputation(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The reputation cannot be null." })
    }

    @Test
    fun `change the trade of a transaction`() {
        val otherTrade: Trade =
            TradeBuilder()
                .withCrypto(anyCrypto().build())
                .withQuantity(150.0)
                .withAmountARS(180.0)
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
    fun `a violation occurs when changing the action in a transaction to null`() {
        val transaction = anyTransaction().withAction(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The action cannot be null." })
    }
}