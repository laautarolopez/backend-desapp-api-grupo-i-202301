package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoOperatedBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.CryptoOperatedServiceImp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CryptoOperatedServiceTest {

    @Autowired
    lateinit var cryptoOperatedService: CryptoOperatedServiceImp

    var trxusdt : CryptoName = CryptoName.TRXUSDT
    var btcusdt : CryptoName = CryptoName.BTCUSDT

    fun anyCryptoOperated(): CryptoOperatedBuilder {
        return CryptoOperatedBuilder()
            .withCryptoName(trxusdt)
            .withQuantity(60.00)
            .withAmount(150.8)
            .withPrice(200.00)
    }

    @Test
    fun `a cryptoOperated is successfully created when it has correct data`() {
        val anyCrypto = anyCryptoOperated().build()

        val crypto = cryptoOperatedService.create(anyCrypto)

        Assertions.assertTrue(crypto.id != null)
    }

    @Test
    fun `change the name of a cryptoOperated`() {
        val cryptoOperatedRequested = anyCryptoOperated().withCryptoName(btcusdt).build()

        val crypto = cryptoOperatedService.create(cryptoOperatedRequested)

        Assertions.assertTrue(crypto.id != null)
    }

    @Test
    fun `a violation occurs when change the cryptoName of a cryptoOperated for null`() {
        val cryptoOperatedRequested = anyCryptoOperated().withCryptoName(null).build()

        try {
            cryptoOperatedService.create(cryptoOperatedRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.cryptoOperated.cryptoName: The crypto name cannot be null.", e.message)
        }
    }

    @Test
    fun `change the quantity of a cryptoOperated`() {
        val cryptoOperatedRequested = anyCryptoOperated().withQuantity(50.00).build()

        val crypto = cryptoOperatedService.create(cryptoOperatedRequested)

        Assertions.assertTrue(crypto.id != null)
    }

    @Test
    fun `a violation occurs when change the quantity of a cryptoOperated to negative`() {
        val cryptoOperatedRequested = anyCryptoOperated().withQuantity(-5.0).build()

        try {
            cryptoOperatedService.create(cryptoOperatedRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.cryptoOperated.quantity: The quantity cannot be negative.", e.message)
        }
    }

    @Test
    fun `a violation occurs when change the quantity of a cryptoOperated for null`() {
        val cryptoOperatedRequested = anyCryptoOperated().withQuantity(null).build()

        try {
            cryptoOperatedService.create(cryptoOperatedRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.cryptoOperated.quantity: The quantity cannot be null.", e.message)
        }
    }

    @Test
    fun `change the amount of a cryptoOperated`() {
        val cryptoOperatedRequested = anyCryptoOperated().withAmount(100.00).build()

        val crypto = cryptoOperatedService.create(cryptoOperatedRequested)

        Assertions.assertTrue(crypto.id != null)
    }

    @Test
    fun `a violation occurs when change the amount of a cryptoOperated to negative`() {
        val cryptoOperatedRequested = anyCryptoOperated().withAmount(-30.0).build()

        try {
            cryptoOperatedService.create(cryptoOperatedRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.cryptoOperated.amountARS: The amount ARS cannot be negative.", e.message)
        }
    }

    @Test
    fun `a violation occurs when change the amount of a cryptoOperated for null`() {
        val cryptoOperatedRequested = anyCryptoOperated().withAmount(null).build()

        try {
            cryptoOperatedService.create(cryptoOperatedRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.cryptoOperated.amountARS: The amount ARS cannot be null.", e.message)
        }
    }

    @Test
    fun `change the price of a cryptoOperated`() {
        val cryptoOperatedRequested = anyCryptoOperated().withPrice(220.50).build()

        val crypto = cryptoOperatedService.create(cryptoOperatedRequested)

        Assertions.assertTrue(crypto.id != null)
    }

    @Test
    fun `a violation occurs when change the price of a cryptoOperated to negative`() {
        val cryptoOperatedRequested = anyCryptoOperated().withPrice(-100.0).build()

        try {
            cryptoOperatedService.create(cryptoOperatedRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.cryptoOperated.price: The price cannot be negative.", e.message)
        }
    }

    @Test
    fun `a violation occurs when change the price of a cryptoOperated for null`() {
        val cryptoOperatedRequested = anyCryptoOperated().withPrice(null).build()

        try {
            cryptoOperatedService.create(cryptoOperatedRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.cryptoOperated.price: The price cannot be null.", e.message)
        }
    }
}