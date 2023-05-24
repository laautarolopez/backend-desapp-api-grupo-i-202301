package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.UserServiceImp
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest {
    @Autowired
    lateinit var userService: UserService

    fun anyUser(): UserBuilder {
        return UserBuilder()
            .withName("Jorge")
            .withLastName("Sanchez")
            .withEmail("jorgesanchez@gmail.com")
            .withAddress("calle falsa 123")
            .withPassword("Password@1234")
            .withCVU("1234567890123456789012")
            .withWalletAddress("12345678")
            .withOperations(15)
    }

    val otherUser1: User =
        UserBuilder()
            .withName("Marta")
            .withLastName("Lopez")
            .withEmail("martalopez@gmail.com")
            .withAddress("calle belgrano 140")
            .withPassword("Password@1234")
            .withCVU("1234567890123456789012")
            .withWalletAddress("12345678")
            .build()

    val otherUser2: User =
        UserBuilder()
            .withName("Jorge")
            .withLastName("Sanchez")
            .withEmail("jorgesanchez@gmail.com")
            .withAddress("calle san martin 13")
            .withPassword("Password@1234")
            .withCVU("1234567890123456789012")
            .withWalletAddress("12345678")
            .build()

    @Test
    fun `a user is successfully created when it has correct data`() {
        val anyUser = anyUser().build()

        val user = userService.create(anyUser)

        assertTrue(user.id != null)
    }

    @Test
    fun `an user is renamed`() {
        val userRequested = anyUser().withName("Nicolas").build()

        val user = userService.create(userRequested)

        assertTrue(user.id != null)
    }

    @Test
    fun `an exception be throw when a user's name with less than 3 characters`() {
        val user = anyUser().withName("gi").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.name: The name must be between 3 and 30 characters long.", e.message)
        }
    }

    @Test
    fun `an exception be throw when a user's name with more than 30 characters`() {
        val user = anyUser().withName("Juan Alberto Ramon Luis Gerardo").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.name: The name must be between 3 and 30 characters long.", e.message)
        }
    }

    @Test
    fun `change the last name of an user`() {
        val userRequested = anyUser().withLastName("Gomez").build()

        val user = userService.create(userRequested)

        assertTrue(user.id != null)
    }

    @Test
    fun `an exception be throw when changing a user's last name with less than 3 characters`() {
        val user = anyUser().withLastName("As").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.lastName: The last name must be between 3 and 30 characters long.", e.message)
        }
    }

    @Test
    fun `an exception be throw when changing the last name of a user with more than 30 characters`() {
        val user = anyUser().withLastName("Taboada Gomez Lopez Perez Gimenez").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.lastName: The last name must be between 3 and 30 characters long.", e.message)
        }
    }


    @Test
    fun `change the email address of an user`() {
        val userRequested = anyUser().withEmail("newEmail@gmail.com").build()

        val user = userService.create(userRequested)

        assertTrue(user.id != null)
    }

    @Test
    fun `that an exception is thrown when changing the email to an empty one`() {
        val user = anyUser().withEmail("").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.email: The email address cannot be blank.", e.message)
        }
    }

    @Test
    fun `an exception be throw when changing the email to an invalid one`() {
        val user = anyUser().withEmail("newEmail").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.email: A valid email address must be used.", e.message)
        }
    }

    @Test
    fun `the address is changed to an user`() {
        val userRequested = anyUser().withAddress("calle 14 num 1111").build()

        val user = userService.create(userRequested)

        assertTrue(user.id != null)
    }

    @Test
    fun `an exception be throw when changing the address of a user with less than 10 characters`() {
        val user = anyUser().withAddress("st").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.address: The address must be between 10 and 30 characters long.", e.message)
        }
    }

    @Test
    fun `an exception be throw when changing a user address longer than 30 characters`() {
        val user = anyUser().withAddress("Calle falsa entre rivera y sarmiento num 1111").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.address: The address must be between 10 and 30 characters long.", e.message)
        }
    }

    @Test
    fun `the password is changed to an user`() {
        val userRequested = anyUser().withPassword("12345.Password").build()

        val user = userService.create(userRequested)

        assertTrue(user.id != null)
    }

    @Test
    fun `an exception be throw when changing a password that does not contain at least one uppercase letter`() {
        val user = anyUser().withPassword("12345.password").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.password: The password must contain at least 1 lower case, 1 upper case and 1 special character.", e.message)
        }
    }

    @Test
    fun `an exception be throw when changing a password that does not contain at least one lowercase letter`() {
        val user = anyUser().withPassword("12345.PASSWORD").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.password: The password must contain at least 1 lower case, 1 upper case and 1 special character.", e.message)
        }
    }

    @Test
    fun `an exception be throw when changing a password that does not contain at least one special character`() {
        val user = anyUser().withPassword("12345Password").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.password: The password must contain at least 1 lower case, 1 upper case and 1 special character.", e.message)
        }
    }

    @Test
    fun `an exception be throw when changing a password that does not contain at least 6 digits`() {
        val user = anyUser().withPassword("Pa@1").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.password: The password must be at least 6 characters long.", e.message)
        }
    }

    @Test
    fun `change the cvu to an user`() {
        val userRequested = anyUser().withCVU("2222222222222222222222").build()

        val user = userService.create(userRequested)

        assertTrue(user.id != null)
    }

    @Test
    fun `an exception be throw when changing the cvu of a user who does not have only digits`() {
        val user = anyUser().withCVU("12345678901234567890zz").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.cvuMercadoPago: The CVU must only have digits.", e.message)
        }
    }

    @Test
    fun `an exception be throw when changing the cvu of a user that does not have just 22 digits`() {
        val user = anyUser().withCVU("123456789012345678901").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.cvuMercadoPago: The CVU must have 22 digits.", e.message)
        }
    }

    @Test
    fun `change the wallet address of an user`() {
        val userRequested = anyUser().withWalletAddress("00000000").build()

        val user = userService.create(userRequested)

        assertTrue(user.id != null)
    }

    @Test
    fun `an exception be throw when changing a user's wallet address that does not have only digits`() {
        val user = anyUser().withWalletAddress("0000000a").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.walletAddress: The wallet address must only have digits.", e.message)
        }
    }

    @Test
    fun `an exception be throw when changing a user's wallet address that is less than 8 digits long`() {
        val user = anyUser().withWalletAddress("1234567").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.walletAddress: The wallet address must have 8 digits.", e.message)
        }
    }

    @Test
    fun `an exception be throw when changing a user's wallet address that is longer than 8 digits`() {
        val user = anyUser().withWalletAddress("123456789").build()

        try {
            userService.create(user)
            fail("An exception must be throw.")
        } catch (e: RuntimeException) {
            assertEquals("create.user.walletAddress: The wallet address must have 8 digits.", e.message)
        }
    }

    @Test
    fun `change the operations of an user`() {
        val userRequested = anyUser().withOperations(20).build()

        val user = userService.create(userRequested)

        assertTrue(user.id != null)
    }


    @Test
    fun `3 users are successfully created and recovered`() {
        userService.create(anyUser().build())

        var users = userService.recoverAll()
        assertTrue(users.size == 1)

        userService.create(otherUser1)
        userService.create(otherUser2)

        users = userService.recoverAll()

        assertTrue(users.size == 3)
    }

    @Test
    fun `no user is recovered`() {
        var users = userService.recoverAll()

        println(users.size)
        assertTrue(users.isEmpty())
    }

    @Test
    fun `an user is getted`() {
        val user = userService.create(anyUser().build())
        val idUser = user.id

        val userGetted = userService.getUser(idUser!!)

        assertEquals(user, userGetted)
    }

    @Test
    fun `an exception be thrown when an user is not exist`() {
        try {
            userService.getUser(55)
            fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            assertEquals("User non-existent.", e.message)
        }
    }

    @Test
    fun `update user by change the name`() {
        val userRequested = anyUser().withName("Carlos").build()
        val user = userService.create(userRequested)
        user.name = "Roberto"
        userService.update(user)

        val userRecovered = userService.getUser(user.id!!)

        assertEquals("Roberto", userRecovered.name)
    }

    @Test
    fun `an exception be thrown when update null user`() {
        try {
            val user = anyUser().build()
            userService.update(user)
            fail("An exception must be throw.")
        } catch(e: RuntimeException) {
            assertEquals("User non-existent.", e.message)
        }
    }

    @AfterEach
    fun clear() {
        userService.clear()
    }
}