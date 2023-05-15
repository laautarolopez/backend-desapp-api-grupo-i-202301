package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoVolumeBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.CryptoVolumeServiceImp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class CryptoVolumeServiceTest {
    @Autowired
    lateinit var cryptoVolumeService: CryptoVolumeServiceImp

    fun anyCryptoVolume(): CryptoVolumeBuilder {
        return CryptoVolumeBuilder()
            .withTimeRequest(LocalDateTime.now())
            .withAmountARS(150.00)
            .withAmountUSD(200.00)
            .withCryptos(listOf())
    }

    @Test
    fun `a cryptoVolume is successfully created when it has correct data`() {
        val anyCrypto = anyCryptoVolume().build()

        val crypto = cryptoVolumeService.create(anyCrypto)

        Assertions.assertTrue(crypto.id != null)
    }

    @Test
    fun `change the time request of a cryptoVolume`() {
        val oldTime = anyCryptoVolume().timeRequest
        val cryptoVolumeRequested = anyCryptoVolume().withTimeRequest(LocalDateTime.now()).build()

        val crypto = cryptoVolumeService.create(cryptoVolumeRequested)

        Assertions.assertTrue(crypto.id != null)
        Assertions.assertTrue(oldTime != cryptoVolumeRequested.timeRequest)
    }

    @Test
    fun `a violation occurs when change the time request of a cryptoVolume for null`() {
        val cryptoVolumeRequested = anyCryptoVolume().withTimeRequest(null).build()

        try {
            cryptoVolumeService.create(cryptoVolumeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.crypto.timeRequest: The time request cannot be null.", e.message)
        }
    }

    @Test
    fun `change the amount USD of a cryptoVolume`() {
        val cryptoVolumeRequested = anyCryptoVolume().withAmountUSD(220.14).build()

        val crypto = cryptoVolumeService.create(cryptoVolumeRequested)

        Assertions.assertTrue(crypto.id != null)
    }

    @Test
    fun `a violation occurs when change the amount USD of a cryptoVolume to negative`() {
        val cryptoVolumeRequested = anyCryptoVolume().withAmountUSD(-60.0).build()

        try {
            cryptoVolumeService.create(cryptoVolumeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.crypto.amountUSD: The amount USD cannot be negative.", e.message)
        }
    }

    @Test
    fun `a violation occurs when change the amount USD of a cryptoVolume for null`() {
        val cryptoVolumeRequested = anyCryptoVolume().withAmountUSD(null).build()

        try {
            cryptoVolumeService.create(cryptoVolumeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.crypto.amountUSD: The amount USD cannot be null.", e.message)
        }
    }

    @Test
    fun `change the amount ARS of a cryptoVolume`() {
        val cryptoVolumeRequested = anyCryptoVolume().withAmountARS(150.45).build()

        val crypto = cryptoVolumeService.create(cryptoVolumeRequested)

        Assertions.assertTrue(crypto.id != null)
    }

    @Test
    fun `a violation occurs when change the amount ARS of a cryptoVolume to negative`() {
        val cryptoVolumeRequested = anyCryptoVolume().withAmountARS(-90.0).build()

        try {
            cryptoVolumeService.create(cryptoVolumeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.crypto.amountARS: The amount ARS cannot be negative.", e.message)
        }
    }

    @Test
    fun `a violation occurs when change the amount ARS of a cryptoVolume for null`() {
        val cryptoVolumeRequested = anyCryptoVolume().withAmountARS(null).build()

        try {
            cryptoVolumeService.create(cryptoVolumeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.crypto.amountARS: The amount ARS cannot be null.", e.message)
        }
    }
}