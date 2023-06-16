package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TradeBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.BinanceResponseInt
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.DolarBlueResponse
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.DolarResponseInt
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.PriceResponse
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDateTime

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class TransactionServiceTest {
    @Autowired
    lateinit var transactionService: TransactionService

    @Autowired
    lateinit var cryptoService: CryptoService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var tradeService: TradeService

    @MockBean
    lateinit var dolarResponse: DolarResponseInt
    @MockBean
    lateinit var binanceResponse: BinanceResponseInt

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
            .withOperations(0)
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
            .withOperations(0)
            .build()

    val anyCrypto: Crypto =
        CryptoBuilder()
            .withName(CryptoName.BTCUSDT)
            .build()

    fun anyTrade(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto)
            .withCryptoPrice(200.00)
            .withQuantity(200.50)
            .withUser(anyUser)
            .withOperation(sale)
            .withCreationDate(LocalDateTime.now())
            .withIsActive(true)
    }

    val anyTrade: Trade =
        TradeBuilder()
            .withCrypto(anyCrypto)
            .withCryptoPrice(200.00)
            .withQuantity(200.50)
            .withUser(anyUser)
            .withOperation(sale)
            .withCreationDate(LocalDateTime.now())
            .withIsActive(true).build()

    val otherTrade: Trade =
        TradeBuilder()
            .withCrypto(anyCrypto)
            .withCryptoPrice(100.00)
            .withQuantity(300.0)
            .withUser(otherUser)
            .withOperation(buy)
            .withCreationDate(LocalDateTime.now())
            .withIsActive(true).build()

    fun anyTransaction(): TransactionBuilder {
        return TransactionBuilder()
            .withIdUserRequested(1)
            .withBuyer(otherUser)
            .withSeller(anyUser)
            .withTrade(anyTrade)
    }

    fun otherTransaction1(): TransactionBuilder {
         return TransactionBuilder()
            .withIdUserRequested(2)
             .withBuyer(otherUser)
             .withSeller(anyUser)
            .withTrade(anyTrade)
    }

    fun otherTransaction2(): TransactionBuilder {
        return TransactionBuilder()
            .withIdUserRequested(1)
            .withBuyer(anyUser)
            .withSeller(otherUser)
            .withTrade(otherTrade)
    }

    var user1: User? = null
    var user2: User? = null

    @BeforeEach
    fun setup() {
        fun price(cryptoName: String) = PriceResponse(cryptoName, 1.123, LocalDateTime.now().toString())

        CryptoName.values().forEach {
                name -> Mockito.`when`(binanceResponse.getPrice(name.toString())).thenReturn(price(name.toString()))
        }

        val dolarBlue = DolarBlueResponse("Dolar Blue", 490.00)
        Mockito.`when`(dolarResponse.getPrice()).thenReturn(dolarBlue)

        cryptoService.create(anyCrypto)
        user1 = userService.create(anyUser)
        user2 = userService.create(otherUser)
        tradeService.create(anyTrade)
        tradeService.create(otherTrade)
    }

    @Test
    fun `a transaction is successfully created when it has correct data`() {
        val anyTransaction = anyTransaction().withIdUserRequested(user2!!.id).build()

        val transaction = transactionService.create(anyTransaction)

        Assertions.assertTrue(transaction.id != null)
    }

    @Test
    fun `an exception occurs when the trade not exists`() {
        val anyTrade = anyTrade().build()
        val anyTransaction = anyTransaction().withIdUserRequested(user1!!.id).withTrade(anyTrade).build()

        try {
            transactionService.create(anyTransaction)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("The trade does not exist.", e.message)
        }
    }

    @Test
    fun `an exception occurs when the user not exists`() {
        val anyTransaction = anyTransaction().withIdUserRequested(50).withTrade(anyTrade).build()

        try {
            transactionService.create(anyTransaction)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("User non-existent.", e.message)
        }
    }

    @Test
    fun `an exception occurs when the user which creates the trade want to create a transaction`() {
        val idUserTrade = user1!!.id
        val anyTransaction = anyTransaction().withIdUserRequested(idUserTrade).withTrade(anyTrade).build()

        try {
            transactionService.create(anyTransaction)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("The transaction cannot be created by this user", e.message)
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
                .withCreationDate(LocalDateTime.now())
                .withIsActive(true).build()
        tradeService.create(otherTrade)

        val transactionRequested = anyTransaction().withIdUserRequested(user2!!.id).withTrade(otherTrade).build()

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
    fun `a transaction changes status to transfer`() {
        val transaction = transactionService.create(anyTransaction().withIdUserRequested(user2!!.id).build())
        val idTransaction = transaction.id

        var transactionRecovered = transactionService.getTransaction(idTransaction)
        Assertions.assertEquals(transactionRecovered.status, TransactionStatus.CREATED)

        var transactionTransfer = transactionService.transfer(transaction)
        Assertions.assertEquals(transactionTransfer.status, TransactionStatus.TRANSFERRED)
    }

    @Test
    fun `an exception occurs transfering when the transaction does not exist`() {
        val transaction = anyTransaction().withIdUserRequested(user2!!.id).build()

        try {
            transactionService.transfer(transaction)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The transaction does not exist.", e.message)
        }
    }

    @Test
    fun `an exception occurs transfering when the transaction status is TRANSFERRED`() {
        val status = TransactionStatus.TRANSFERRED
        val transaction = anyTransaction().withIdUserRequested(user2!!.id).withStatus(status).build()
        transactionService.create(transaction)

        try {
            transactionService.transfer(transaction)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The status must be CREATED", e.message)
        }
    }

    @Test
    fun `an exception occurs transfering when the transaction status is CONFIRMED`() {
        val status = TransactionStatus.CONFIRMED
        val transaction = anyTransaction().withIdUserRequested(user2!!.id).withStatus(status).build()
        transactionService.create(transaction)

        try {
            transactionService.transfer(transaction)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The status must be CREATED", e.message)
        }
    }

    @Test
    fun `an exception occurs transfering when the transaction status is CANCELED`() {
        val status = TransactionStatus.CANCELED
        val transaction = anyTransaction().withIdUserRequested(user2!!.id).withStatus(status).build()
        transactionService.create(transaction)

        try {
            transactionService.transfer(transaction)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The status must be CREATED", e.message)
        }
    }

    @Test
    fun `a transaction changes status to canceled`() {
        var userRecovered = userService.getUser(user2!!.id)
        userRecovered.addPoints(30)
        userRecovered.addOperation()
        userService.update(userRecovered)

        val transaction = transactionService.create(anyTransaction().withIdUserRequested(user2!!.id).build())
        val idTransaction = transaction.id

        println(transaction.seller!!.getReputation())
        println(transaction.buyer!!.getReputation())

        var transactionRecovered = transactionService.getTransaction(idTransaction)
        Assertions.assertEquals(transactionRecovered.status, TransactionStatus.CREATED)
        Assertions.assertEquals(transactionRecovered.buyer!!.getReputation().toInt(), 30)

        var transactionTransfer = transactionService.cancel(transaction)
        Assertions.assertEquals( TransactionStatus.CANCELED, transactionTransfer.status)
        Assertions.assertEquals(transactionTransfer.buyer!!.getReputation().toInt(), 10)
    }

    @Test
    fun `an exception occurs canceling when the transaction status is CANCELED`() {
        val status = TransactionStatus.CANCELED
        val transaction = anyTransaction().withIdUserRequested(user2!!.id).withStatus(status).build()
        transactionService.create(transaction)

        try {
            transactionService.cancel(transaction)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The status must be TRANSFERRED or CREATED", e.message)
        }
    }

    @Test
    fun `an exception occurs canceling when the transaction status is CONFIRMED`() {
        val status = TransactionStatus.CONFIRMED
        val transaction = anyTransaction().withIdUserRequested(user2!!.id).withStatus(status).build()
        transactionService.create(transaction)

        try {
            transactionService.cancel(transaction)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The status must be TRANSFERRED or CREATED", e.message)
        }
    }

    @Test
    fun `a transaction changes status to confirmed`() {
        val transaction = transactionService.create(anyTransaction().withIdUserRequested(user2!!.id).build())
        val idTransaction = transaction.id

        var transactionRecovered = transactionService.getTransaction(idTransaction)
        Assertions.assertEquals(TransactionStatus.CREATED, transactionRecovered.status)

        transactionRecovered = transactionService.transfer(transactionRecovered)
        Assertions.assertEquals(TransactionStatus.TRANSFERRED, transactionRecovered.status)

        transactionRecovered.idUserRequested = user1!!.id
        transactionRecovered = transactionService.update(transactionRecovered)

        Assertions.assertTrue(transactionRecovered.buyer!!.operations == 0)
        Assertions.assertEquals(transactionRecovered.buyer!!.getReputation(), "Without operations")
        Assertions.assertTrue(transactionRecovered.seller!!.operations == 0)
        Assertions.assertTrue(transactionRecovered.seller!!.getReputation() == "Without operations")

        var transactionTransfer = transactionService.confirm(transactionRecovered)
        Assertions.assertEquals( TransactionStatus.CONFIRMED, transactionTransfer.status)

        Assertions.assertTrue(transactionTransfer.buyer!!.operations == 1)
        Assertions.assertTrue(transactionTransfer.buyer!!.getReputation().toInt() == 10)
        Assertions.assertTrue(transactionTransfer.seller!!.operations == 1)
        Assertions.assertTrue(transactionTransfer.seller!!.getReputation().toInt() == 10)
    }

    @Test
    fun `an exception occurs confirming when the transaction status is CONFIRMED`() {
        val status = TransactionStatus.CONFIRMED
        val transaction = anyTransaction().withIdUserRequested(user2!!.id).withStatus(status).build()
        transactionService.create(transaction)

        try {
            transactionService.confirm(transaction)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The status must be TRANSFERRED", e.message)
        }
    }

    @Test
    fun `an exception occurs confirming when the transaction status is CANCELED`() {
        val status = TransactionStatus.CANCELED
        val transaction = anyTransaction().withIdUserRequested(user2!!.id).withStatus(status).build()
        transactionService.create(transaction)

        try {
            transactionService.confirm(transaction)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The status must be TRANSFERRED", e.message)
        }
    }

    @Test
    fun `a transaction is getted`() {
        val transaction = transactionService.create(anyTransaction().withIdUserRequested(user2!!.id).build())
        val idTransaction = transaction.id

        val transactionGetted = transactionService.getTransaction(idTransaction)

        Assertions.assertEquals(transaction, transactionGetted)
    }

    @Test
    fun `an exception be thrown when a transaction is not exists`() {
        try {
            transactionService.getTransaction(50)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The transaction does not exist.", e.message)
        }
    }

    @Test
    fun `a transaction is updated`() {
        val transaction = transactionService.create(anyTransaction().withIdUserRequested(user2!!.id).build())
        val idTransaction = transaction.id
        transaction.status = TransactionStatus.TRANSFERRED

        transactionService.update(transaction)

        val transactionGetted = transactionService.getTransaction(idTransaction)

        Assertions.assertEquals(TransactionStatus.TRANSFERRED, transactionGetted.status)
    }

    @Test
    fun `an exception occurs when change the transaction not exists`() {
        val transaction = anyTransaction().build()
        transaction.status = TransactionStatus.CONFIRMED

        try {
            transactionService.update(transaction)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The transaction does not exist.", e.message)
        }
    }

    @Test
    fun `no transaction is recovered`() {
        var transaction = transactionService.recoverAll()

        Assertions.assertTrue(transaction.isEmpty())
    }

    @Test
    fun `5 transactions are successfully created and recovered`() {
        var transactions = transactionService.recoverAll()
        Assertions.assertTrue(transactions.isEmpty())

        val transaction1 = anyTransaction().withIdUserRequested(user2!!.id).build()
        val transaction2 = otherTransaction1().withIdUserRequested(user2!!.id).build()
        val transaction3 = otherTransaction2().withIdUserRequested(user1!!.id).build()
        transactionService.create(transaction1)
        transactionService.create(transaction2)
        transactionService.create(transaction3)

        transactions = transactionService.recoverAll()

        Assertions.assertTrue(transactions.size == 3)
    }

    @AfterEach
    fun cleanup() {
        transactionService.clear()
        tradeService.clear()
        userService.clear()
        cryptoService.clear()
    }
}