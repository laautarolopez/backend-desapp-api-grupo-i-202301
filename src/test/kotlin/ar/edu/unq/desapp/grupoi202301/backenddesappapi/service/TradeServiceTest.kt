package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TradeBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.TradeNonExistentException
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
class TradeServiceTest {
    @Autowired
    lateinit var tradeService: TradeService
    @Autowired
    lateinit var cryptoService: CryptoService
    @Autowired
    lateinit var userService: UserService

    @MockBean
    lateinit var dolarResponse: DolarResponseInt
    @MockBean
    lateinit var binanceResponse: BinanceResponseInt

    var sale : OperationType = OperationType.SALE
    var buy : OperationType = OperationType.BUY

    val anyCrypto: Crypto =
        CryptoBuilder()
            .withName(CryptoName.BTCUSDT)
            .build()

    fun anyCrypto(): CryptoBuilder {
        return CryptoBuilder()
            .withName(CryptoName.ALICEUSDT)
    }

    val anyUser: User =
        UserBuilder()
            .withName("Jorge")
            .withLastName("Sanchez")
            .withEmail("jorgesanchez@gmail.com")
            .withAddress("calle falsa 123")
            .withPassword("Password@1234")
            .withCVU("1234567890123456789012")
            .withWalletAddress("12345678")
            .withOperations(1)
            .build()

    fun anyUser(): UserBuilder {
        return UserBuilder()
            .withName("Jorge")
            .withLastName("Sanchez")
            .withEmail("jorgesanchez@gmail.com")
            .withAddress("calle falsa 123")
            .withPassword("Password@1234")
            .withCVU("1234567890123456789012")
            .withWalletAddress("12345678")
            .withOperations(1)
    }

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

    fun otherTrade1(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto)
            .withCryptoPrice(250.00)
            .withQuantity(250.50)
            .withUser(anyUser)
            .withOperation(sale)
            .withCreationDate(LocalDateTime.now())
            .withIsActive(true)
    }

    fun otherTrade2(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto)
            .withCryptoPrice(100.00)
            .withQuantity(150.0)
            .withUser(anyUser)
            .withOperation(buy)
            .withCreationDate(LocalDateTime.now())
            .withIsActive(true)
    }

    fun otherTrade3(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto)
            .withCryptoPrice(300.00)
            .withQuantity(230.50)
            .withUser(anyUser)
            .withOperation(buy)
            .withCreationDate(LocalDateTime.now())
            .withIsActive(true)
    }

    fun otherTrade4(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto)
            .withQuantity(150.50)
            .withUser(anyUser)
            .withOperation(sale)
            .withCreationDate(LocalDateTime.now())
            .withIsActive(true)
    }

    @BeforeEach
    fun setup() {
        fun price(cryptoName: String) = PriceResponse(cryptoName, 1.123, LocalDateTime.now().toString())

        CryptoName.values().forEach {
            name -> Mockito.`when`(binanceResponse.getPrice(name.toString())).thenReturn(price(name.toString()))
        }

        val dolarBlue = DolarBlueResponse("Dolar Blue", 490.00)
        Mockito.`when`(dolarResponse.getPrice()).thenReturn(dolarBlue)

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
    fun `an exception occurs when the user not exists`() {
        val user = anyUser().build()
        val tradeRequested = anyTrade().withUser(user).build()

        try {
            tradeService.create(tradeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("User non-existent.", e.message)
        }
    }

    @Test
    fun `an exception occurs when the crypto not exists`() {
        val crypto = anyCrypto().build()
        val tradeRequested = anyTrade().withCrypto(crypto).build()

        try {
            tradeService.create(tradeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("Crypto non-existent.", e.message)
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
    fun `an trade is getted`() {
        val trade = tradeService.create(anyTrade().build())
        val idTrade = trade.id

        val tradeGetted = tradeService.getTrade(idTrade)

        Assertions.assertEquals(trade, tradeGetted)
    }

    @Test
    fun `an exception be thrown when an trade is not exist`() {
        try {
            tradeService.getTrade(55)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The trade does not exist.", e.message)
        }
    }

    @Test
    fun `no trade is recovered`() {
        var trades = tradeService.recoverAll()

        Assertions.assertTrue(trades.isEmpty())
    }

    @Test
    fun `5 trades are successfully created and recovered`() {
        var trades = tradeService.recoverAll()
        Assertions.assertTrue(trades.isEmpty())

        tradeService.create(anyTrade().build())
        tradeService.create(otherTrade1().build())
        tradeService.create(otherTrade2().build())
        tradeService.create(otherTrade3().build())
        tradeService.create(otherTrade4().build())

        trades = tradeService.recoverAll()

        Assertions.assertTrue(trades.size == 5)
    }

    @Test
    fun `3 active user trades are recovered successfully`() {
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

    @Test
    fun `a trade is updated`() {
        val trade = tradeService.create(anyTrade().build())
        val idTrade = trade.id
        trade.isActive = false

        tradeService.update(trade)

        val tradeGetted = tradeService.getTrade(idTrade)

        Assertions.assertEquals(false, tradeGetted.isActive)
    }

    @Test
    fun `an exception occurs when change the trade not exists`() {
        val trade = anyTrade().build()
        trade.isActive = false

        try {
            tradeService.update(trade)
            Assertions.fail("An exception must be throw.")
        } catch(e: TradeNonExistentException) {
            Assertions.assertEquals("The trade does not exist.", e.message)
        }
    }

    @AfterEach
    fun clear() {
        tradeService.clear()
        userService.clear()
        cryptoService.clear()
    }
}