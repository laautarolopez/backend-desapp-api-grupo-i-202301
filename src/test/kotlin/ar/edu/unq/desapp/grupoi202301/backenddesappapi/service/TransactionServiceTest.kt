package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TradeBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.UserBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
@TestInstance(PER_CLASS)
class TransactionServiceTest {
    @Autowired
    lateinit var transactionService: TransactionService
    @Autowired
    lateinit var cryptoService: CryptoService
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var tradeService: TradeService

    var aaveusdt: CryptoName = CryptoName.AAVEUSDT
    var trxusdt: CryptoName = CryptoName.TRXUSDT
    var confirm: ActionTransaction = ActionTransaction.CONFIRM
    var make: ActionTransaction = ActionTransaction.MAKE
    var sale: OperationType = OperationType.SALE

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

    val anyCrypto: Crypto =
        CryptoBuilder()
            .withName(CryptoName.BTCUSDT)
            .withTime(LocalDateTime.now())
            .withPrice(300.50)
            .build()


    fun anyCrypto(): CryptoBuilder {
        return CryptoBuilder()
            .withName(CryptoName.BTCUSDT)
            .withTime(LocalDateTime.now())
            .withPrice(300.50)
    }

    val anyTrade: Trade =
        TradeBuilder()
            .withCrypto(anyCrypto)
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

    @BeforeAll
    fun setup() {
        cryptoService.create(anyCrypto)
        userService.create(anyUser)
        tradeService.create(anyTrade)
    }

    @Test
    fun `a transaction is successfully created when it has correct data`() {
        val anyTransaction = anyTransaction().build()

        val transaction = transactionService.create(anyTransaction)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `change the cryptoname of a transaction`() {
        val transactionRequested = anyTransaction().withCrypto(trxusdt).build()

        val transaction = transactionService.create(transactionRequested)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `a violation occurs when change the cryptoname of a transaction for null`() {
        val transactionRequested = anyTransaction().withCrypto(null).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.transaction.crypto: The crypto cannot be null.", e.message)
        }
    }

    @Test
    fun `change the quantity of a transaction`() {
        val transactionRequested = anyTransaction().withQuantity(250.06).build()

        val transaction = transactionService.create(transactionRequested)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `a violation occurs when change the quantity of a transaction to negative`() {
        val transactionRequested = anyTransaction().withQuantity(-20.0).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.transaction.quantity: The quantity cannot be negative.", e.message)
        }
    }

    @Test
    fun `a violation occurs when change the quantity of a transaction for null`() {
        val transactionRequested = anyTransaction().withQuantity(null).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.transaction.quantity: The quantity cannot be null.", e.message)
        }
    }

    @Test
    fun `change the quotation crypto of a transaction`() {
        val transactionRequested = anyTransaction().withQuotationCrypto(150.86).build()

        val transaction = transactionService.create(transactionRequested)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `a violation occurs when change the quotation crypto of a transaction to negative`() {
        val transactionRequested = anyTransaction().withQuotationCrypto(-30.0).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.transaction.quotationCrypto: The quotation crypto cannot be negative.", e.message)
        }
    }

    @Test
    fun `a violation occurs when change the quotation crypto of a transaction for null`() {
        val transactionRequested = anyTransaction().withQuotationCrypto(null).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.transaction.quotationCrypto: The quotation crypto cannot be null.", e.message)
        }
    }

    @Test
    fun `change the amount operation of a transaction`() {
        val transactionRequested = anyTransaction().withAmountOperation(55.86).build()

        val transaction = transactionService.create(transactionRequested)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `a violation occurs when change the amount operation of a transaction to negative`() {
        val transactionRequested = anyTransaction().withAmountOperation(-15.00).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(
                "create.transaction.amountOperation: The amount of operation cannot be negative.",
                e.message
            )
        }
    }


    @Test
    fun `a violation occurs when change the amount operation of a transaction for null`() {
        val transactionRequested = anyTransaction().withAmountOperation(null).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(
                "create.transaction.amountOperation: The amount of operation cannot be null.",
                e.message
            )
        }
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
                .withReputation(6)
                .withOperations(2)
                .build()
        userService.create(otherUser)


        val transactionRequested = anyTransaction().withUser(otherUser).build()

        val transaction = transactionService.create(transactionRequested)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `a violation occurs when changing the user in a transaction to null`() {
        val transactionRequested = anyTransaction().withUser(null).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(
                "create.transaction.user: The user cannot be null.",
                e.message
            )
        }
    }

    @Test
    fun `change the number operations of a transaction`() {
        val transactionRequested = anyTransaction().withNumberOperations(25).build()

        val transaction = transactionService.create(transactionRequested)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `a violation occurs when the number of operations in a transaction is changed to a negative`() {
        val transactionRequested = anyTransaction().withNumberOperations(-1).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(
                "create.transaction.numberOperations: The number must be equal to or greater than 0.",
                e.message
            )
        }
    }

    @Test
    fun `a violation occurs when changing the number of operations in a transaction to null`() {
        val transactionRequested = anyTransaction().withNumberOperations(null).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(
                "create.transaction.numberOperations: The numbers of operations cannot be null.",
                e.message
            )
        }
    }

    @Test
    fun `change the reputation of a transaction`() {
        val transactionRequested = anyTransaction().withReputation(3).build()

        val transaction = transactionService.create(transactionRequested)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `a violation occurs when the reputation in a transaction is changed to a negative`() {
        val transactionRequested = anyTransaction().withReputation(-5).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(
                "create.transaction.reputation: The number must be equal to or greater than 0.",
                e.message
            )
        }
    }

    @Test
    fun `a violation occurs when changing the reputation in a transaction to null`() {
        val transactionRequested = anyTransaction().withReputation(null).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(
                "create.transaction.reputation: The reputation cannot be null.",
                e.message
            )
        }
    }

    @Test
    fun `change the trade of a transaction`() {
        val otherTrade: Trade =
            TradeBuilder()
                .withCrypto(anyCrypto)
                .withQuantity(150.0)
                .withAmountARS(180.0)
                .withUser(anyUser)
                .withOperation(sale).build()
        tradeService.create(otherTrade)

        val transactionRequested = anyTransaction().withTrade(otherTrade).build()

        val transaction = transactionService.create(transactionRequested)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `a violation occurs when changing the trade a transaction to null`() {
        val transactionRequested = anyTransaction().withTrade(null).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(
                "create.transaction.trade: The trade cannot be null.",
                e.message
            )
        }
    }

    @Test
    fun `change the shipping address of a transaction`() {
        val transactionRequested = anyTransaction().withShippingAddress("2131267169283121567927").build()

        val transaction = transactionService.create(transactionRequested)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `a violation occurs when changing the shipping address in a transaction to null`() {
        val transactionRequested = anyTransaction().withShippingAddress(null).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(
                "create.transaction.shippingAddress: The shipping address cannot be null.",
                e.message
            )
        }
    }

    @Test
    fun `change the action of a transaction`() {
        val transactionRequested = anyTransaction().withAction(make).build()

        val transaction = transactionService.create(transactionRequested)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `a violation occurs when changing the action in a transaction to null`() {
        val transactionRequested = anyTransaction().withAction(null).build()

        try {
            transactionService.create(transactionRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals(
                "create.transaction.action: The action cannot be null.",
                e.message
            )
        }
    }
}