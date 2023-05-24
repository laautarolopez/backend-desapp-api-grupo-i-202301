package ar.edu.unq.desapp.grupoi202301.backenddesappapi

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TradeService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class Aspect(
    @Autowired
    private val cryptoService: CryptoService,
    @Autowired
    private val userService: UserService,
    @Autowired
    private val tradeService: TradeService,
    @Autowired
    private val transactionService: TransactionService,
) : ApplicationRunner {

    var aliceusdt: Crypto = Crypto()
    var user1: User = User()
    var user2: User = User()
    var trade: Trade = Trade()
    var transaction: Transaction = Transaction()

    @Transactional
    override fun run(args: ApplicationArguments?) {
        insertData()
    }

    private fun insertData() {
        saveCryptos()
        saveUsers()
//        saveTrades()
//        saveTransactions()
    }


    private fun saveCryptos() {
        CryptoName.values().forEach {
            cryptoName ->
                val crypto = Crypto()
                crypto.name = cryptoName
                cryptoService.create(crypto)
                if(cryptoName == CryptoName.ALICEUSDT) {
                    aliceusdt = crypto
                }
        }
    }

    private fun saveUsers() {
        fun anyUser(): UserBuilder {
            return UserBuilder()
                .withName("Jorge")
                .withLastName("Sanchez")
                .withEmail("jorgesanchez@gmail.com")
                .withAddress("calle falsa 123")
                .withPassword("Password@1234")
                .withCVU("1234567890123456789012")
                .withWalletAddress("12345678")
        }
        user1 = anyUser().withName("Lautaro").build()
        user2 = anyUser().withName("Fabricio").build()

        userService.create(user1)
        userService.create(user2)
    }

//    private fun saveTrades() {
//        trade = TradeBuilder()
//            .withCrypto(aliceusdt)
//            .withCryptoPrice(200.00)
//            .withQuantity(200.50)
//            .withUser(user1)
//            .withOperation(OperationType.SALE)
//            .withCreationDate(LocalDateTime.now())
//            .withIsActive(true)
//            .build()
//        tradeService.create(trade)
//    }
//
//    private fun saveTransactions() {
//        transaction = TransactionBuilder()
//            .withIdUserRequested(user2.id)
//            .withBuyer(user2)
//            .withSeller(user1)
//            .withTrade(trade)
//            .build()
//        transactionService.create(transaction)
//    }
}