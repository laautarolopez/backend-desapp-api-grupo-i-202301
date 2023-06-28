package ar.edu.unq.desapp.grupoi202301.backenddesappapi.aspect

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.*
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
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
) : ApplicationRunner {

    var aliceusdt: Crypto = Crypto()
    var user1: User = User()
    var user2: User = User()
    var user3: User = User()
    var trade: Trade = Trade()
    var transaction: Transaction = Transaction()

    @Transactional
    override fun run(args: ApplicationArguments?) {
        insertData()
    }

    private fun insertData() {
        saveCryptos()
        saveUsers()
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
        fun anyUser2(): UserBuilder {
            return UserBuilder()
                .withName("Lautaro")
                .withLastName("Sanchez")
                .withEmail("lautarosanchez@gmail.com")
                .withAddress("calle falsa 123")
                .withPassword("Password@1234")
                .withCVU("1234567890123456789012")
                .withWalletAddress("12345678")
        }
        fun anyUser3(): UserBuilder {
            return UserBuilder()
                    .withName("Nicolas")
                    .withLastName("Gomez")
                    .withEmail("admin@admin.com")
                    .withAddress("calle falsa 123")
                    .withPassword("Password@1234")
                    .withCVU("1234567890123456789012")
                    .withWalletAddress("12345678")
        }
        user1 = anyUser().withName("Lautaro").build()
        user2 = anyUser2().withName("Fabricio").build()
        user3 = anyUser3().build()

        userService.create(user1)
        userService.create(user2)
        userService.create(user3)
    }
}