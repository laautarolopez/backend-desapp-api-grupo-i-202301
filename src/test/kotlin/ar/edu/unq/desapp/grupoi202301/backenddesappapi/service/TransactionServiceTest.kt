package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TradeBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.UserBuilder
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

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

    var confirm: ActionTransaction = ActionTransaction.CONFIRM
    var make: ActionTransaction = ActionTransaction.MAKE
    var sale: OperationType = OperationType.SALE
    var buy: OperationType = OperationType.BUY

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
            .build()

    val anyTrade: Trade =
        TradeBuilder()
            .withCrypto(anyCrypto)
            .withQuantity(200.50)
            .withUser(anyUser)
            .withOperation(sale)
            .withIsActive(true).build()

    val otherTrade: Trade =
        TradeBuilder()
            .withCrypto(anyCrypto)
            .withQuantity(300.0)
            .withUser(anyUser)
            .withOperation(buy)
            .withIsActive(true).build()

    fun anyTransaction(): TransactionBuilder {
        return TransactionBuilder()
            .withAmountOperation(200.4)
            .withTrade(anyTrade)
            .withShippingAddress("1234567890123456789012")
            .withAction(confirm)
    }

    @BeforeAll
    fun setup() {
        cryptoService.create(anyCrypto)
        userService.create(anyUser)
        tradeService.create(anyTrade)
        tradeService.create(otherTrade)
    }

    @Test
    fun `a transaction is successfully created when it has correct data`() {
        val anyTransaction = anyTransaction().build()

        val transaction = transactionService.create(anyTransaction)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `a violation occurs when creating a sale transaction with a wrong shipping address`() {
        val anyTransaction = anyTransaction().withShippingAddress("12345678").build()

        try {
            transactionService.create(anyTransaction)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("The shipping address must contain a CVU with 22 digits.", e.message)
        }
    }

    @Test
    fun `a violation occurs when creating a buy transaction with a wrong shipping address`() {
        val anyTransaction = anyTransaction().withTrade(otherTrade).build()

        try {
            transactionService.create(anyTransaction)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("The shipping address must contain a walletAddress with 8 digits.", e.message)
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
    fun `change the trade of a transaction`() {
        val otherTrade: Trade =
            TradeBuilder()
                .withCrypto(anyCrypto)
                .withQuantity(150.0)
                .withUser(anyUser)
                .withOperation(sale)
                .withIsActive(true).build()
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

    @AfterEach
    fun cleanup() {
        cryptoService.clear()
        userService.clear()
        tradeService.clear()
        transactionService.clear()
    }
}