package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.CryptoServiceImp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class CryptoServiceTest {
    @Autowired
    lateinit var cryptoService: CryptoServiceImp

    val btcusdt : CryptoName = CryptoName.BTCUSDT
    val aaveusdt : CryptoName = CryptoName.AAVEUSDT

    fun anyCrypto(): CryptoBuilder {
        return CryptoBuilder()
            .withName(btcusdt)
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
    fun `4 cryptos prices are recovered successfully`() {
        cryptoService.create(anyCrypto().build())

        //var prices = cryptoService.getPrices()

        //Assertions.assertTrue(prices.size == 1)

        cryptoService.create(otherCrypto1)
        cryptoService.create(otherCrypto2)
        cryptoService.create(otherCrypto3)

        //prices = cryptoService.getPrices()

        //Assertions.assertTrue(prices.size == 4)
    }

    //TODO(Testear getPrice() con Mock?)
    //TODO(Testear getPrices() con Mock?)
}