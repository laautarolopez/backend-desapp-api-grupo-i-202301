package ar.edu.unq.desapp.grupoi202301.backenddesappapi

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.lang.RuntimeException

@SpringBootTest
class ModelTest {
    lateinit var anyUser: User;

    @BeforeEach
    fun setup() {
        anyUser = User("name", "lastName", "email@gmail.com", "adress 123", "password", "1234567890123456789012", "12345678")
    }

    @Test
    fun `a user is successfully created and when prompted for data, the data loaded on creation is returned`() {
        var newUser = User("Juan", "Gomez", "juangomez@gmail.com", "calle falsa 123", "juan123", "1111111111111111111111", "22222222")

        assertEquals("Juan", newUser.name())
        assertEquals("Gomez", newUser.name())
        assertEquals("juangomez@gmail.com", newUser.email())
        assertEquals("calle falsa 123", newUser.adress)
        assertEquals("juan123", newUser.password)
        assertEquals("1111111111111111111111", newUser.cvuMercadoPago)
        assertEquals("22222222", newUser.walletAdress)
    }

    @Test
    fun `an existing user is renamed`() {
        anyUser.changeName("newName")
        assertEquals("newName", anyUser.name())
    }

    @Test
    fun `an exception is raised when changing the name of a user with less than 3 characters`() {
        try {
            anyUser.changeName("Gi")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "The name must be between 3 and 30 characters long.")
        }
    }

    @Test
    fun `an exception is raised when changing the name of a user with more than 30 characters`() {
        try {
            anyUser.changeName("Juan Alberto Ramon Luis Gerardo")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "The name must be between 3 and 30 characters long.")
        }
    }

    @Test
    fun `change the last name of an existing user`() {
        anyUser.changeLastName("newLastName")

        assertEquals("newLastName", anyUser.lastName())
    }

    @Test
    fun `an exception is raised when changing a user's last name with fewer than 3 characters`() {
        try {
            anyUser.changeLastName("As")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "The last name must be between 3 and 30 characters long.")
        }
    }

    @Test
    fun `an exception is raised when changing the last name of a user with more than 30 characters`() {
        try {
            anyUser.changeLastName("Taboada Gomez Lopez Perez Gimenez")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "The last name must be between 3 and 30 characters long.")
        }
    }

    @Test
    fun `change the email address of an existing user`() {
        anyUser.changeEmail("nuevoemail@gmail.com")
        assertEquals("nuevoemail@gmail.com", anyUser.email())
    }

    @Test
    fun `an exception occurs when changing the email to an invalid one`() {
        try {
            anyUser.changeEmail("nuevoemail")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "A valid email address must be used.")
        }
    }

    @Test
    fun `the address is changed to an existing user`() {
        anyUser.changeAdress("calle 14 num 1111")

        assertEquals("calle 14 num 1111", anyUser.adress)
    }

    @Test
    fun `an exception is raised when changing the address of a user with less than 10 characters`() {
        try {
            anyUser.changeAdress("st")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "The address must be between 10 and 30 characters long.")
        }
    }

    @Test
    fun `an exception is raised when changing a user address longer than 30 characters`() {
        try {
            anyUser.changeAdress("Calle falsa entre rivera y sarmiento num 1111")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "The address must be between 10 and 30 characters long.")
        }
    }

    @Test
    fun `the password is changed to an existing user`() {
        anyUser.changePassword("password123")
        assertEquals("password123", anyUser.password)
    }

    @Test
    fun `change the cvu to an existing user`() {
        anyUser.changeCVUMercadoPago("2222222222222222222222")
        assertEquals("2222222222222222222222", anyUser.cvuMercadoPago)
    }

    @Test
    fun `an exception is raised when changing the cvu of a user who does not have only digits`() {
        try {
            anyUser.changeCVUMercadoPago("12345678901234567890z")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "The CVU must only have digits.")
        }
    }

    @Test
    fun `an exception is raised when changing the cvu of a user that does not have 22 digits`() {
        try {
            anyUser.changeCVUMercadoPago("123456789012345678901")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "The CVU must have 22 digits.")
        }
    }

    @Test
    fun `change the wallet address of an existing user`() {
        anyUser.changeWalletAdress("00000000")
        assertEquals("00000000", anyUser.walletAdress)
    }

    @Test
    fun `an exception is raised when changing a user's wallet address that does not have only digits`() {
        try {
            anyUser.changeWalletAdress("1234567f")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "The wallet adress must only have digits.")
        }
    }

    @Test
    fun `an exception is raised when changing a user's wallet address that is less than 8 digits long`() {
        try {
            anyUser.changeWalletAdress("1234567")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "The wallet address must be 8 digits long.")
        }
    }

    @Test
    fun `an exception is raised when changing a user's wallet address that is longer than 8 digits`() {
        try {
            anyUser.changeWalletAdress("123456789")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "The wallet address must be 8 digits long.")
        }
    }
}
