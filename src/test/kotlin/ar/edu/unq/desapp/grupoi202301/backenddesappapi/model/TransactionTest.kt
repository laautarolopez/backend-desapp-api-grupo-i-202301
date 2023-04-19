package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TransactionBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TransactionTest {
    @Autowired
    lateinit var validator: Validator

    var aaveusdt : CryptoName = CryptoName.AAVEUSDT
    var trxusdt : CryptoName = CryptoName.TRXUSDT
    var confirm : ActionTransaction = ActionTransaction.CONFIRM
    var make : ActionTransaction = ActionTransaction.MAKE

    fun anyTransaction(): TransactionBuilder {
        return TransactionBuilder()
            .withCrypto(aaveusdt)
            .withQuantity(300.10)
            .withQuotationCrypto(15.4)
            .withAmountOperation(200.4)
            .withUser("Jorge")
            .withNumberOperations(5)
            .withReputation(7)
            .withShippingAddress("1234567890123456789012")
            .withAction(confirm)
    }

    @Test
    fun `a transaction is successfully created when it has correct data`() {
        assertDoesNotThrow { anyTransaction().build() }
    }

    @Test
    fun `change the name of an transaction`() {
        val transaction = anyTransaction().withCrypto(trxusdt).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation to change the name of a transaction for something empty`() {
        val crypto = anyTransaction().withCrypto(null).build()

        val violations = validator.validate(crypto)

        Assertions.assertTrue(violations.any { v -> v.message == "The crypto cannot be null." })
    }

    @Test
    fun `change the quantity of an transaction`() {
        val transaction = anyTransaction().withQuantity(250.06).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation to change the quantity of a transaction for something empty`() {
        val transaction = anyTransaction().withQuantity(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The quantity cannot be null." })
    }

    @Test
    fun `change the quotation crypto of an transaction`() {
        val transaction = anyTransaction().withQuotationCrypto(150.86).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation to change the quotation crypto of a transaction for something empty`() {
        val transaction = anyTransaction().withQuotationCrypto(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The quotation crypto cannot be null." })
    }

    @Test
    fun `change the amount operation of an transaction`() {
        val transaction = anyTransaction().withAmountOperation(55.86).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation to change the amount operation of a transaction for something empty`() {
        val transaction = anyTransaction().withAmountOperation(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The amount of operation cannot be null." })
    }

    @Test
    fun `change the name the user of an transaction`() {
        val transaction = anyTransaction().withUser("Jorge").build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the name of a user in a transaction to empty`() {
        val transaction = anyTransaction().withUser(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The user cannot be null." })
    }

    @Test
    fun `a violation occurs when changing the name of a user with less than 3 characters`() {
        val transaction = anyTransaction().withUser("jo").build()

        val violations = validator.validate(transaction)

        Assertions.assertEquals(1, violations.size)
        Assertions.assertTrue(violations.any { v -> v.message == "The name must be between 3 and 30 characters long." })
    }

    @Test
    fun `a violation occurs when changing the name of a user with more than 30 characters`() {
        val transaction = anyTransaction().withUser("Juan Alberto Ramon Luis Gerardo").build()

        val violations = validator.validate(transaction)

        Assertions.assertEquals(1, violations.size)
        Assertions.assertTrue(violations.any { v -> v.message == "The name must be between 3 and 30 characters long." })
    }

    @Test
    fun `change the number operations of an transaction`() {
        val transaction = anyTransaction().withNumberOperations(25).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the number of operations in a transaction to empty`() {
        val transaction = anyTransaction().withNumberOperations(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The numbers of operations cannot be null." })
    }

    @Test
    fun `change the reputation of an transaction`() {
        val transaction = anyTransaction().withReputation(3).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the reputation in a transaction to empty`() {
        val transaction = anyTransaction().withReputation(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The reputation cannot be null." })
    }

    @Test
    fun `change the shipping address of an transaction`() {
        val transaction = anyTransaction().withShippingAddress("2131267169283121567927").build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the shipping address in a transaction to empty`() {
        val transaction = anyTransaction().withShippingAddress(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The shipping address cannot be null." })
    }

    @Test
    fun `change the action of an transaction`() {
        val transaction = anyTransaction().withAction(make).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the action in a transaction to empty`() {
        val transaction = anyTransaction().withAction(null).build()

        val violations = validator.validate(transaction)

        Assertions.assertTrue(violations.any { v -> v.message == "The action cannot be null." })
    }
}