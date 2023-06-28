package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.Quote24hsBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.Quote24hsServiceImp
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Quote24hsServiceTest {
    @Autowired
    lateinit var quote24hsService: Quote24hsServiceImp

    val aaveusdt : CryptoName = CryptoName.AAVEUSDT
    val btcusdt : CryptoName = CryptoName.BTCUSDT
    val aliceusdt : CryptoName = CryptoName.ALICEUSDT
    val ethusdt : CryptoName = CryptoName.ETHUSDT

    fun anyQuote24hs(): Quote24hsBuilder {
        return Quote24hsBuilder()
            .withName(aaveusdt)
            .withPrice(250.00)
            .withTime("2023-07-02T18:19:26.492745400")
    }

    fun otherQuote24hs1(): Quote24hsBuilder {
        return Quote24hsBuilder()
            .withName(aaveusdt)
            .withPrice(250.00)
            .withTime("2023-07-02T18:19:26.492745400")
    }

    fun otherQuote24hs2(): Quote24hsBuilder {
        return Quote24hsBuilder()
            .withName(ethusdt)
            .withPrice(250.00)
            .withTime("2023-07-02T18:19:26.492745400")
    }

    fun otherQuote24hs3(): Quote24hsBuilder {
        return Quote24hsBuilder()
            .withName(btcusdt)
            .withPrice(250.00)
            .withTime("2023-07-02T18:19:26.492745400")
    }

    @Test
    fun `a quote24hs is successfully created when it has correct data`() {
        val anyQuote24hs = anyQuote24hs().build()

        val quote24hs = quote24hsService.create(anyQuote24hs)

        Assertions.assertTrue(quote24hs.id != null)
    }

    @Test
    fun `change the name of a quote24hs`() {
        val quote24hsRequested = anyQuote24hs().withName(aaveusdt).build()

        val quote24hs = quote24hsService.create(quote24hsRequested)

        Assertions.assertTrue(quote24hs.id != null)
    }

    @Test
    fun `a violation occurs when change the name of a quote24hs for null`() {
        val quote24hsRequested = anyQuote24hs().withName(null).build()

        try {
            quote24hsService.create(quote24hsRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.quote24hs.cryptoName: The crypto name cannot be null.", e.message)
        }
    }

    @Test
    fun `a quote24hs is successfully getted`() {
        val anyQuote24hs = anyQuote24hs().build()

        val quote24hs = quote24hsService.create(anyQuote24hs)
        val idQuote24hs = quote24hs.id

        val quote24hsGetted = quote24hsService.getQuote24hs(idQuote24hs!!)

        Assertions.assertEquals(quote24hs, quote24hsGetted)
    }

    @Test
    fun `an exception be thrown when a quote24hs is not exist`() {
        try {
            quote24hsService.getQuote24hs(55)
            Assertions.fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            Assertions.assertEquals("The quote does not exist.", e.message)
        }
    }

    @Test
    fun `4 quotes4hs are recovered successfully`() {
        quote24hsService.create(anyQuote24hs().build())
        quote24hsService.create(otherQuote24hs1().build())
        quote24hsService.create(otherQuote24hs2().build())
        quote24hsService.create(otherQuote24hs3().build())

        var quotes24hs = quote24hsService.recoverAll()


        Assertions.assertTrue(quotes24hs.size == 4)
    }


    @AfterAll
    fun clear() {
        quote24hsService.clear()
    }
}