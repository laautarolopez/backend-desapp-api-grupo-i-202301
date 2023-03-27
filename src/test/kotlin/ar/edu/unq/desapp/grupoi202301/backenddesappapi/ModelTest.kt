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
        anyUser = User("name", "lastName", "email", "adress 123", "password", "1234567890123456789012", "12345678")
    }

    @Test
    fun `se crea un usuario y al preguntar los datos, se devuelve los datos cargados al crearlo`() {
        var nuevoUsuario = User("Juan", "Gomez", "juangomez@gmail.com", "calle falsa 123", "juan123", "1111111111111111111111", "22222222")

        assertEquals("Juan", nuevoUsuario.name)
        assertEquals("Gomez", nuevoUsuario.lastName)
        assertEquals("juangomez@gmail.com", nuevoUsuario.email)
        assertEquals("calle falsa 123", nuevoUsuario.adress)
        assertEquals("juan123", nuevoUsuario.password)
        assertEquals("1111111111111111111111", nuevoUsuario.cvuMercadoPago)
        assertEquals("22222222", nuevoUsuario.walletAdress)
    }

    @Test
    fun `se cambia el nombre a un usuario ya existente`() {
        anyUser.changeName("nuevoNombre")

        assertEquals("nuevoNombre", anyUser.name)
    }

    @Test
    fun `se levanta una excepción al cambiar el nombre de un usuario con menos de 3 caracteres`() {
        try {
            anyUser.changeName("Gi")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "El nombre debe tener entre 3 y 30 caracteres.")
        }
    }

    @Test
    fun `se levanta una excepción al cambiar el nombre de un usuario con mas de 30 caracteres`() {
        try {
            anyUser.changeName("Juan Alberto Ramon Luis Gerardo")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "El nombre debe tener entre 3 y 30 caracteres.")
        }
    }

    @Test
    fun `se cambia el apellido a un usuario ya existente`() {
        anyUser.changeLastName("nuevoApellido")

        assertEquals("nuevoApellido", anyUser.lastName)
    }

    @Test
    fun `se levanta una excepción al cambiar el apellido de un usuario con menos de 3 caracteres`() {
        try {
            anyUser.changeLastName("As")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "El apellido debe tener entre 3 y 30 caracteres.")
        }
    }

    @Test
    fun `se levanta una excepción al cambiar el apellido de un usuario con mas de 30 caracteres`() {
        try {
            anyUser.changeLastName("Taboada Gomez Lopez Perez Gimenez")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "El apellido debe tener entre 3 y 30 caracteres.")
        }
    }

    @Test
    fun `se cambia el email a un usuario ya existente`() {
        anyUser.changeEmail("nuevoemail@gmail.com")

        assertEquals("nuevoemail@gmail.com", anyUser.email)
    }

    @Test
    fun `se cambia la direccion a un usuario ya existente`() {
        anyUser.changeAdress("calle 14 num 1111")

        assertEquals("calle 14 num 1111", anyUser.adress)
    }

    @Test
    fun `se levanta una excepción al cambiar la direccion de un usuario con menos de 10 caracteres`() {
        try {
            anyUser.changeAdress("st")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "La dirección debe tener entre 10 y 30 caracteres.")
        }
    }

    @Test
    fun `se levanta una excepción al cambiar la direccion de un usuario con mas de 30 caracteres`() {
        try {
            anyUser.changeAdress("Calle falsa entre rivera y sarmiento num 1111")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "La dirección debe tener entre 10 y 30 caracteres.")
        }
    }

    @Test
    fun `se cambia la clave a un usuario ya existente`() {
        anyUser.changePassword("password123")

        assertEquals("password123", anyUser.password)
    }

    @Test
    fun `se cambia el cvu a un usuario ya existente`() {
        anyUser.changeCVUMercadoPago("2222222222222222222222")

        assertEquals("2222222222222222222222", anyUser.cvuMercadoPago)
    }

    @Test
    fun `se levanta una excepción al cambiar el cvu de un usuario que no tenga 22 digitos`() {
        try {
            anyUser.changeCVUMercadoPago("123456789012345678901")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "El CVU debe tener 22 dígitos.")
        }
    }

    @Test
    fun `se cambia la direccion de la billetera a un usuario ya existente`() {
        anyUser.changeWalletAdress("00000000")

        assertEquals("00000000", anyUser.walletAdress)
    }

    @Test
    fun `se levanta una excepción al cambiar la dirección de la billetera de un usuario que no tenga 8 digitos`() {
        try {
            anyUser.changeWalletAdress("1234567")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(e.message, "La dirección de la billetera debe tener 8 dígitos.")
        }
    }
}