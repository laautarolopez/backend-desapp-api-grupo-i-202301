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

    var sale : OperationType = OperationType.SALE
    var buy : OperationType = OperationType.BUY

    var statusCreated: TransactionStatus = TransactionStatus.CREATED
    var statusCanceled: TransactionStatus = TransactionStatus.CANCELED

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

    val otherUser: User =
        UserBuilder()
            .withName("Marcos")
            .withLastName("Perez")
            .withEmail("marcosperez@gmail.com")
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
            .withCryptoPrice(200.00)
            .withQuantity(200.50)
            .withUser(anyUser)
            .withOperation(sale)
            .withCreationDate(LocalDateTime.now()).build()

    fun anyTransaction(): TransactionBuilder {
        return TransactionBuilder()
            .withIdUserRequested(2)
            .withBuyer(anyUser)
            .withSeller(otherUser)
            .withTrade(anyTrade)
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
    fun `change the trade of a transaction`() {
        val otherTrade: Trade =
            TradeBuilder()
                .withCrypto(anyCrypto().build())
                .withCryptoPrice(200.00)
                .withQuantity(150.0)
                .withUser(anyUser)
                .withOperation(buy)
                .withCreationDate(LocalDateTime.now()).build()


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
    fun `change the buyer of a transaction`() {

        val transaction = anyTransaction().withBuyer(otherUser).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `change the seller of a transaction`() {

        val transaction = anyTransaction().withBuyer(anyUser).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `change the status of a transaction`() {

        val transaction = anyTransaction().withStatus(statusCanceled).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `change the date of a transaction`() {
        val time = LocalDateTime.now()
        val transaction = anyTransaction().withDate(time).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }
}