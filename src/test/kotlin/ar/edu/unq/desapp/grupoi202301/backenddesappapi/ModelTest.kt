package ar.edu.unq.desapp.grupoi202301.backenddesappapi

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ModelTest {
    lateinit var anyUser: User;

    @BeforeEach
    fun setup() {
        anyUser = User("name", "lastName", "email", "direction", "password")
    }

    @Test
    fun contextLoads() {
    }
}