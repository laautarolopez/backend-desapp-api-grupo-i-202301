package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.TransactionServiceImp
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.UserServiceImp
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CryptoVolumeServiceTest {
    @Autowired
    lateinit var transactionService: TransactionServiceImp
    @Autowired
    lateinit var userService: UserServiceImp

    @Test
    fun `The operated volume of crypto assets of a user between two dates is obtained`() {
        //TODO: add implementation
    }
}