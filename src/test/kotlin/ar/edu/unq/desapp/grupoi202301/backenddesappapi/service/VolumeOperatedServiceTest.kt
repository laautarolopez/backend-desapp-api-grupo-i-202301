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
class VolumeOperatedServiceTest {
    @Autowired
    lateinit var volumeOperatedService: VolumeOperatedService
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var tradeService: TradeService
    @Autowired
    lateinit var transactionService: TransactionService
    @Autowired
    lateinit var cryptoService: CryptoService

    @MockBean
    lateinit var dolarResponse: DolarResponseInt
    @MockBean
    lateinit var binanceResponse: BinanceResponseInt

    var user: User? = null
    var user2: User? = null
    var user3: User? = null

    var transaction1: Transaction? = null
    var transaction2: Transaction? = null

    var date1 = "2023-06-20T12:36:39.359"
    var date2 = "2023-07-01T12:36:39.359"

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

    val otherUser2: User =
        UserBuilder()
            .withName("Fiama")
            .withLastName("Lopez")
            .withEmail("fiamalopez@gmail.com")
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
            .withUser(otherUser2)
            .withOperation(buy)
            .withCreationDate(LocalDateTime.now())
            .withIsActive(true).build()

    fun anyTransaction(): TransactionBuilder {
        return TransactionBuilder()
            .withIdUserRequested(null)
            .withBuyer(otherUser)
            .withSeller(anyUser)
            .withTrade(anyTrade)
    }

    fun otherTransaction1(): TransactionBuilder {
        return TransactionBuilder()
            .withIdUserRequested(null)
            .withBuyer(otherUser2)
            .withSeller(otherUser)
            .withTrade(otherTrade)
    }

    @BeforeEach
    fun setup() {
        fun price(cryptoName: String) = PriceResponse(cryptoName, 1.123, LocalDateTime.now().toString())
        val list = listOf(price("BTCUSDT"), price("AAVEUSDT"), price("ALICEUSDT"), price("ETHUSDT"))

        CryptoName.values().forEach {
                name -> Mockito.`when`(binanceResponse.getPrice(name.toString())).thenReturn(price(name.toString()))
        }

        Mockito.`when`(binanceResponse.getPrices()).thenReturn(list)

        val dolarBlue = DolarBlueResponse("Dolar Blue", 490.00)
        Mockito.`when`(dolarResponse.getPrice()).thenReturn(dolarBlue)

        user = userService.create(anyUser)
        user2 = userService.create(otherUser)
        user3 = userService.create(otherUser2)

        cryptoService.create(anyCrypto)
        tradeService.create(anyTrade)
        tradeService.create(otherTrade)

        transaction1 = transactionService.create(anyTransaction().withIdUserRequested(otherUser.id).build())
        transaction2 = transactionService.create(otherTransaction1().withIdUserRequested(otherUser.id).build())
    }

    @Test
    fun `no volume traded by the user between both past dates`() {
        date1 = "2023-04-10T12:36:39.359"
        date2 = "2023-05-01T12:36:39.359"

        var volumeOperated = volumeOperatedService.volumeOperatedByAUserBetweenDates(user2!!.id!!.toLong(), date1, date2)

        Assertions.assertEquals(volumeOperated.cryptos!!.size, 0)
    }

    @Test
    fun `volume traded by the user between both past dates`() {
        //all persisted transactions are saved
        var transactions = transactionService.recoverAll()

        Assertions.assertTrue(transactions.size == 2)

        var volumeOperated = volumeOperatedService.volumeOperatedByAUserBetweenDates(user!!.id!!.toLong(), date1, date2)

        Assertions.assertEquals(volumeOperated.cryptos!!.size, 0)
        Assertions.assertEquals(volumeOperated.amountARS, 0.0)
        Assertions.assertEquals(volumeOperated.amountUSD, 0.0)

        var transactionRecovered1 = transactionService.getTransaction(transaction1!!.id)

        var transactionTransfered = transactionService.transfer(transactionRecovered1)
        transactionTransfered.idUserRequested = user!!.id
        transactionRecovered1 = transactionService.update(transactionTransfered)

        transactionService.confirm(transactionRecovered1)

        volumeOperated = volumeOperatedService.volumeOperatedByAUserBetweenDates(user!!.id!!.toLong(), date1, date2)

        Assertions.assertEquals(volumeOperated.cryptos!!.size, 1)
    }

    @Test
    fun `an exception occurs when a non-existing user is passed in`() {
        try {
            volumeOperatedService.volumeOperatedByAUserBetweenDates(999, date1, date2)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("User non-existent.", e.message)
        }
    }

    @Test
    fun `an exception occurs when an invalid date is entered`() {
        try {
            volumeOperatedService.volumeOperatedByAUserBetweenDates(otherUser.id!!, date1, "")
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("At least one date is invalid.", e.message)
        }
    }

    @AfterEach
    fun cleanup() {
        transactionService.clear()
        tradeService.clear()
        userService.clear()
        cryptoService.clear()
    }

}