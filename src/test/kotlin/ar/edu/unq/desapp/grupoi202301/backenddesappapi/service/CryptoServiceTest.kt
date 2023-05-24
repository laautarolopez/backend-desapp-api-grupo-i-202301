package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.BinanceResponseInt
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.PriceResponse
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.CryptoServiceImp
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDateTime

@SpringBootTest
@ExtendWith(MockitoExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CryptoServiceTest {
    @MockBean
    lateinit var binanceResponse: BinanceResponseInt

    @Autowired
    lateinit var cryptoService: CryptoServiceImp

    val aaveusdt : CryptoName = CryptoName.AAVEUSDT
    val btcusdt : CryptoName = CryptoName.BTCUSDT
    val aliceusdt : CryptoName = CryptoName.ALICEUSDT
    val ethusdt : CryptoName = CryptoName.ETHUSDT

    fun anyCrypto(): CryptoBuilder {
        return CryptoBuilder()
            .withName(btcusdt)
    }

    val otherCrypto1: Crypto =
        CryptoBuilder()
            .withName(aaveusdt)
            .build()

    val otherCrypto2: Crypto =
        CryptoBuilder()
            .withName(aliceusdt)
            .build()

    val otherCrypto3: Crypto =
        CryptoBuilder()
            .withName(ethusdt)
            .build()

    @BeforeEach
    fun setup() {
        fun price(cryptoName: String) = PriceResponse(cryptoName, 1.123, LocalDateTime.now().toString())
        val list = listOf(price("BTCUSDT"), price("AAVEUSDT"), price("ALICEUSDT"), price("ETHUSDT"))

        CryptoName.values().forEach {
            name -> Mockito.`when`(binanceResponse.getPrice(name.toString())).thenReturn(price(name.toString()))
        }

        Mockito.`when`(binanceResponse.getPrices()).thenReturn(list)
    }

    @Test
    fun `a crypto is successfully created when it has correct data`() {
        val anyCrypto = anyCrypto().build()

        val crypto = cryptoService.create(anyCrypto)

        Assertions.assertTrue(crypto.id != null)
    }

    @Test
    fun `change the name of a crypto`() {
        val cryptoRequested = anyCrypto().withName(aaveusdt).build()

        val crypto = cryptoService.create(cryptoRequested)

        Assertions.assertTrue(crypto.id != null)
    }

    @Test
    fun `a violation occurs when change the name of a crypto for null`() {
        val cryptoRequested = anyCrypto().withName(null).build()

        try {
            cryptoService.create(cryptoRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.crypto.name: The crypto name cannot be null.", e.message)
        }
    }

    @Test
    fun `a crypto is successfully getted`() {
        val anyCrypto = anyCrypto().build()

        val crypto = cryptoService.create(anyCrypto)
        val idCrypto = crypto.id

        val cryptoGetted = cryptoService.getCrypto(idCrypto!!)

        Assertions.assertEquals(crypto, cryptoGetted)
    }

    @Test
    fun `an exception be thrown when a crypto is not exist`() {
        try {
           cryptoService.getCrypto(55)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("Crypto non-existent.", e.message)
        }
    }

    @Test
    fun `get price of ALICEUSDT`() {
        cryptoService.create(anyCrypto().withName(aliceusdt).build())

        var priceResponse = cryptoService.getPrice(aliceusdt.toString())

        Assertions.assertTrue(priceResponse.cryptoName == "ALICEUSDT")
        Assertions.assertTrue(priceResponse.price == 1.123)
    }

    @Test
    fun `4 cryptos prices are recovered successfully`() {
        cryptoService.create(anyCrypto().build())
        cryptoService.create(otherCrypto1)
        cryptoService.create(otherCrypto2)
        cryptoService.create(otherCrypto3)

        var prices = cryptoService.getPrices()

        Assertions.assertTrue(prices.size == 4)
    }

    @AfterAll
    fun clear() {
        cryptoService.clear()
    }
}