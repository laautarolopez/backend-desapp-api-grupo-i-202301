package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.UserResponseDTO
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    val HTTP_LOCALHOST = "http://localhost:"

    @LocalServerPort
    var port = 0

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    val userRegister = """
            {
              "name": "Lautaro",
              "lastName": "López",
              "email": "email@gmail.com",
              "address": "calle falsa 123",
              "password": "Pass123!",
              "cvuMercadoPago": "1111111111111111111111",
              "walletAddress": "11111111"
            }
        """.trimIndent()

    @Test
    fun registerUser() {
        val url = HTTP_LOCALHOST + port + "/users/register"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val requestEntity = HttpEntity(userRegister, headers)

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, String::class.java)

        val responseJson = ObjectMapper().readValue(responseEntity.body, User::class.java)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals("Lautaro", responseJson.name)
        assertEquals("López", responseJson.lastName)
        assertEquals("email@gmail.com", responseJson.email)
    }

    @Test
    fun getUsersTest() {
        val url = HTTP_LOCALHOST + port + "/users"

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, null, String::class.java)

        val userListType = object : TypeReference<List<UserResponseDTO>>() {}
        val responseJson = ObjectMapper().readValue(responseEntity.body, userListType)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertTrue(responseJson.any{ user -> user.name == "Lautaro" })
        assertTrue(responseJson.any{ user -> user.name == "Fabricio" })
    }
}