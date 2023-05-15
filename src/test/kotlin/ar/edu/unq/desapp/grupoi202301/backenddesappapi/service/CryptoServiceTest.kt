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
            .withTime(LocalDateTime.now())
            .withPrice(300.50)
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
    fun `change the time of a crypto`() {
        val oldTime = anyCrypto().time
        val cryptoRequested = anyCrypto().withTime(LocalDateTime.now()).build()

        val crypto = cryptoService.create(cryptoRequested)

        Assertions.assertTrue(crypto.id != null)
        Assertions.assertTrue(oldTime != crypto.time)
    }

    @Test
    fun `a violation occurs when change the time of a crypto for null`() {
        val cryptoRequested = anyCrypto().withTime(null).build()

        try {
            cryptoService.create(cryptoRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.crypto.time: The time cannot be null.", e.message)
        }
    }

    @Test
    fun `change the price of a crypto`() {
        val cryptoRequested = anyCrypto().withPrice(120.84).build()

        val crypto = cryptoService.create(cryptoRequested)

        Assertions.assertTrue(crypto.id != null)
    }

    @Test
    fun `a violation occurs when change the price of a crypto to negative`() {
        val cryptoRequested = anyCrypto().withPrice(-100.0).build()

        try {
            cryptoService.create(cryptoRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.crypto.price: The price cannot be negative.", e.message)
        }
    }

    @Test
    fun `a violation occurs when change the price of a crypto for null`() {
        val cryptoRequested = anyCrypto().withPrice(null).build()

        try {
            cryptoService.create(cryptoRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.crypto.price: The price cannot be null.", e.message)
        }
    }
}