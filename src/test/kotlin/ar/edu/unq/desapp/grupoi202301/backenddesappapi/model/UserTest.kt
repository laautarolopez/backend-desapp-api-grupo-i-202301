package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.UserBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

@SpringBootTest
class UserTest {
    @Autowired
    lateinit var validator: Validator

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

    @Test
    fun `a user is successfully created when it has correct data`() {
        assertDoesNotThrow { anyUser().build() }
    }

    @Test
    fun `an user is renamed`() {
        val user = anyUser().withName("Nicolas").build()

        val violations = validator.validate(user)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the name of a user with less than 3 characters`() {
        val user = anyUser().withName("Gi").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The name must be between 3 and 30 characters long." })
    }

    @Test
    fun `a violation occurs when changing the name of a user with more than 30 characters`() {
        val user = anyUser().withName("Juan Alberto Ramon Luis Gerardo").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The name must be between 3 and 30 characters long." })
    }

    @Test
    fun `change the last name of an user`() {
        val user = anyUser().withLastName("Gomez").build()

        val violations = validator.validate(user)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing a user's last name with less than 3 characters`() {
        val user = anyUser().withLastName("As").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The last name must be between 3 and 30 characters long." })
    }

    @Test
    fun `a violation occurs when changing the last name of a user with more than 30 characters`() {
        val user = anyUser().withLastName("Taboada Gomez Lopez Perez Gimenez").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The last name must be between 3 and 30 characters long." })
    }


    @Test
    fun `change the email address of an user`() {
        val user = anyUser().withEmail("nuevoemail@gmail.com").build()

        val violations = validator.validate(user)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the email to an invalid one`() {
        val user = anyUser().withEmail("nuevoemail").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "A valid email address must be used." })
    }

    @Test
    fun `the address is changed to an user`() {
        val user = anyUser().withAddress("calle 14 num 1111").build()

        val violations = validator.validate(user)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the address of a user with less than 10 characters`() {
        val user = anyUser().withAddress("st").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The address must be between 10 and 30 characters long." })
    }

    @Test
    fun `a violation occurs when changing a user address longer than 30 characters`() {
        val user = anyUser().withAddress("Calle falsa entre rivera y sarmiento num 1111").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The address must be between 10 and 30 characters long." })
    }

    @Test
    fun `the password is changed to an user`() {
        val user = anyUser().withPassword("12345.Password").build()

        val violations = validator.validate(user)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing a password that does not contain at least one uppercase letter`() {
        val user = anyUser().withPassword("12345.password").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The password must contain at least 1 lower case, 1 upper case and 1 special character." })
    }

    @Test
    fun `an violation occurs when changing a password that does not contain at least one lowercase letter`() {
        val user = anyUser().withPassword("12345.PASSWORD").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The password must contain at least 1 lower case, 1 upper case and 1 special character." })
    }

    @Test
    fun `a violation occurs when changing a password that does not contain at least one special character`() {
        val user = anyUser().withPassword("12345Password").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The password must contain at least 1 lower case, 1 upper case and 1 special character." })
    }

    @Test
    fun `a violation occurs when changing a password that does not contain at least 6 digits`() {
        val user = anyUser().withPassword("Pa@1").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The password must be at least 6 characters long." })
    }

    @Test
    fun `change the cvu to an user`() {
        val user = anyUser().withCVU("2222222222222222222222").build()

        val violations = validator.validate(user)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing the cvu of a user who does not have only digits`() {
        val user = anyUser().withCVU("12345678901234567890zz").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The CVU must only have digits." })
    }

    @Test
    fun `a violation occurs when changing the cvu of a user that does not have just 22 digits`() {
        val user = anyUser().withCVU("123456789012345678901").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The CVU must have 22 digits." })
    }

    @Test
    fun `change the wallet address of an user`() {
        val user = anyUser().withWalletAddress("00000000").build()

        val violations = validator.validate(user)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when changing a user's wallet address that does not have only digits`() {
        val user = anyUser().withWalletAddress("0000000a").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The wallet address must only have digits." })
    }

    @Test
    fun `a violation occurs when changing a user's wallet address that is less than 8 digits long`() {
        val user = anyUser().withWalletAddress("1234567").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The wallet address must have 8 digits." })
    }

    @Test
    fun `a violation occurs when changing a user's wallet address that is longer than 8 digits`() {
        val user = anyUser().withWalletAddress("123456789").build()

        val violations = validator.validate(user)

        assertEquals(1, violations.size)
        assertTrue(violations.any { v -> v.message == "The wallet address must have 8 digits." })
    }

    @Test
    fun `change the operations of an user`() {
        val user = anyUser().withOperations(13).build()

        val violations = validator.validate(user)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `a violation occurs when the operations in a user is changed to a negative`() {
        val user = anyUser().withOperations(-1).build()

        val violations = validator.validate(user)

        assertTrue(violations.any { v -> v.message == "The number must be equal to or greater than 0." })
    }

    @Test
    fun `change the operations of an user and getReputation show without operations`() {
        val user = anyUser().withOperations(0).build()

        val reputation = user.getReputation()

        assertEquals("Without operations", reputation)
    }
}
