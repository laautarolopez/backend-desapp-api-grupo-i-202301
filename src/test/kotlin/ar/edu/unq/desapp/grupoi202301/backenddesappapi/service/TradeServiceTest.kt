package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TradeBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.UserBuilder
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(PER_CLASS)
class TradeServiceTest {
    @Autowired
    lateinit var tradeService: TradeService
    @Autowired
    lateinit var cryptoService: CryptoService
    @Autowired
    lateinit var userService: UserService

    var sale : OperationType = OperationType.SALE
    var buy : OperationType = OperationType.BUY

    val anyCrypto: Crypto =
        CryptoBuilder()
            .withName(CryptoName.BTCUSDT)
            .build()

    val anyUser: User =
        UserBuilder()
            .withName("Jorge")
            .withLastName("Sanchez")
            .withEmail("jorgesanchez@gmail.com")
            .withAddress("calle falsa 123")
            .withPassword("Password@1234")
            .withCVU("1234567890123456789012")
            .withWalletAddress("12345678")
            .withReputation(3)
            .withOperations(1)
            .build()

    fun anyTrade(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto)
            .withQuantity(200.50)
            .withUser(anyUser)
            .withOperation(sale)
            .withIsActive(true)
    }

    fun otherTrade1(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto)
            .withQuantity(250.50)
            .withUser(anyUser)
            .withOperation(sale)
            .withIsActive(true)
    }

    fun otherTrade2(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto)
            .withQuantity(150.0)
            .withUser(anyUser)
            .withOperation(buy)
            .withIsActive(true)
    }

    fun otherTrade3(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto)
            .withQuantity(230.50)
            .withUser(anyUser)
            .withOperation(buy)
    }

    fun otherTrade4(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto)
            .withQuantity(150.50)
            .withUser(anyUser)
            .withOperation(sale)
    }

    @BeforeAll
    fun setup() {
        cryptoService.create(anyCrypto)
        userService.create(anyUser)
    }

    @Test
    fun `a trade is successfully created when it has correct data`() {
        val anyTrade = anyTrade().build()

        val trade = tradeService.create(anyTrade)

        Assertions.assertTrue(trade.id != null)
    }

    @Test
    fun `a violation occurs when change the crypto of a trade for null`() {
        val tradeRequested = anyTrade().withCrypto(null).build()

        try {
            tradeService.create(tradeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.trade.crypto: The crypto cannot be null.", e.message)
        }
    }

    @Test
    fun `change the quantity of a trade`() {
        val tradeRequested = anyTrade().withQuantity(250.20).build()

        val trade = tradeService.create(tradeRequested)

        Assertions.assertTrue(trade.id != null)
    }

    @Test
    fun `a violation occurs when change the quantity of a trade to negative`() {
        val tradeRequested = anyTrade().withQuantity(-100.0).build()

        try {
            tradeService.create(tradeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.trade.quantity: The quantity cannot be negative.", e.message)
        }
    }

    @Test
    fun `a violation occurs when change the quantity of a trade for null`() {
        val tradeRequested = anyTrade().withQuantity(null).build()

        try {
            tradeService.create(tradeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.trade.quantity: The quantity cannot be null.", e.message)
        }
    }

    @Test
    fun `change the user of a trade`() {
        val otherUser: User =
            UserBuilder()
                .withName("Jimena")
                .withLastName("Lopez")
                .withEmail("jimenalopez@gmail.com")
                .withAddress("calle San Martin")
                .withPassword("Password@4444")
                .withCVU("4321567890123456781112")
                .withWalletAddress("87651234")
                .build()
        userService.create(otherUser)

        val tradeRequested = anyTrade().withUser(otherUser).build()

        val trade = tradeService.create(tradeRequested)

        Assertions.assertTrue(trade.id != null)
    }

    @Test
    fun `a violation occurs when changing the user in a trade to null`() {
        val tradeRequested = anyTrade().withUser(null).build()

        try {
            tradeService.create(tradeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.trade.user: The user cannot be null.", e.message)
        }
    }

    @Test
    fun `change the operation of a trade`() {
        val tradeRequested = anyTrade().withOperation(buy).build()

        val trade = tradeService.create(tradeRequested)

        Assertions.assertTrue(trade.id != null)
    }

    @Test
    fun `a violation occurs when change the operation of a trade for null`() {
        val tradeRequested = anyTrade().withOperation(null).build()

        try {
            tradeService.create(tradeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.trade.operation: The operation cannot be null.", e.message)
        }
    }

    @Test
    fun `3 active user trades are recovered`() {
        tradeService.create(anyTrade().build())
        tradeService.create(otherTrade1().build())
        tradeService.create(otherTrade2().build())

        var trades = tradeService.recoverAll()
        Assertions.assertTrue(trades.size == 3)

        val tradeNotActive1 = otherTrade3().withIsActive(false).build()
        tradeService.create(tradeNotActive1)
        val tradeNotActive2 = otherTrade4().withIsActive(false).build()
        tradeService.create(tradeNotActive2)

        trades = tradeService.recoverAll()
        Assertions.assertTrue(trades.size == 5)

        val tradesActives = tradeService.recoverActives(anyUser.id!!)
        Assertions.assertTrue(tradesActives.size == 3)
    }

    @AfterEach
    fun cleanup() {
        cryptoService.clear()
        userService.clear()
        tradeService.clear()
    }
}