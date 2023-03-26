package ar.edu.unq.desapp.grupoi202301.backenddesappapi

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import org.junit.jupiter.api.Assertions.assertEquals
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
    fun `se crea un usuario y al preguntar los datos, se devuelve los datos cargados al crearlo`() {
        var nuevoUsuario = User("Juan", "Gomez", "juangomez@gmail.com", "calle falsa 123", "juan123")

        assertEquals("Juan", nuevoUsuario.name)
        assertEquals("Gomez", nuevoUsuario.lastName)
        assertEquals("juangomez@gmail.com", nuevoUsuario.email)
        assertEquals("calle falsa 123", nuevoUsuario.adress)
        assertEquals("juan123", nuevoUsuario.password)
    }

    @Test
    fun `se cambia el nombre a un usuario ya existente`() {
        anyUser.name = "nuevoNombre"

        assertEquals("nuevoNombre", anyUser.name)
    }

    @Test
    fun `se cambia el apellido a un usuario ya existente`() {
        anyUser.lastName = "nuevoApellido"

        assertEquals("nuevoApellido", anyUser.lastName)
    }

    @Test
    fun `se cambia el email a un usuario ya existente`() {
        anyUser.email = "nuevoemail@gmail.com"

        assertEquals("nuevoemail@gmail.com", anyUser.email)
    }

    @Test
    fun `se cambia la direccion a un usuario ya existente`() {
        anyUser.adress = "calle 14 num 1111"

        assertEquals("calle 14 num 1111", anyUser.adress)
    }

    @Test
    fun `se cambia la clave a un usuario ya existente`() {
        anyUser.password = "password123"

        assertEquals("password123", anyUser.password)
    }
}