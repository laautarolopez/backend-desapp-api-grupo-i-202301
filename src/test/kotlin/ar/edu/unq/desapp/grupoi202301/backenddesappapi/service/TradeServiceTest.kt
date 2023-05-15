package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.OperationType
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.CryptoBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.TradeBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.CryptoServiceImp
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.TradeServiceImp
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.UserServiceImp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class TradeServiceTest {
    @Autowired
    lateinit var tradeService: TradeServiceImp
    @Autowired
    lateinit var cryptoService: CryptoServiceImp
    @Autowired
    lateinit var userService: UserServiceImp

    var sale : OperationType = OperationType.SALE
    var buy : OperationType = OperationType.BUY

    fun anyCrypto(): CryptoBuilder {
        return CryptoBuilder()
            .withName(CryptoName.BTCUSDT)
            .withTime(LocalDateTime.now())
            .withPrice(300.50)
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
            .withReputation(3)
            .withOperations(1)
            .build()

    fun anyTrade(): TradeBuilder {
        return TradeBuilder()
            .withCrypto(anyCrypto().build())
            .withQuantity(200.50)
            .withAmountARS(150.8)
            .withUser(anyUser)
            .withOperation(sale)
    }

    @Test
    fun `a trade is successfully created when it has correct data`() {
        val anyTrade = anyTrade().build()

        val trade = tradeService.create(anyTrade)

        Assertions.assertTrue(trade.id != null)
    }

    @Test
    fun `change the crypto of a trade`() {
        val anyCrypto = anyCrypto().withPrice(10.0).build()

        val tradeRequested = anyTrade().withCrypto(anyCrypto).build()

        val trade = tradeService.create(tradeRequested)

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
    fun `change the amountARS of a trade`() {
        val tradeRequested = anyTrade().withAmountARS(220.80).build()

        val trade = tradeService.create(tradeRequested)

        Assertions.assertTrue(trade.id != null)
    }

    @Test
    fun `a violation occurs when change the amountARS of a trade for null`() {
        val tradeRequested = anyTrade().withAmountARS(null).build()

        try {
            tradeService.create(tradeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.trade.amountARS: The amount cannot be null.", e.message)
        }
    }

    @Test
    fun `a violation occurs when change the amountARS of a trade to negative`() {
        val tradeRequested = anyTrade().withAmountARS(-10.0).build()

        try {
            tradeService.create(tradeRequested)
            Assertions.fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            Assertions.assertEquals("create.trade.amountARS: The amount cannot be negative.", e.message)
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
}