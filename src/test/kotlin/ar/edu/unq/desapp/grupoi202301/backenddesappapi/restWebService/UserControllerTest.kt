package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
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

    @Test
    fun registerUser() {
        val url = HTTP_LOCALHOST + port + "/users/register"

        val requestBody = """
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

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val requestEntity = HttpEntity(requestBody, headers)

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, String::class.java)

        val objectMapper = ObjectMapper()
        val responseJson = objectMapper.readValue(responseEntity.body, Map::class.java)


        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals("Lautaro", responseJson["name"])
        assertEquals("López", responseJson["lastName"])
        assertEquals("email@gmail.com", responseJson["email"])
    }

//    @Test
//    fun testEndpoint() {
//        val requestBody = """
//            {
//                "name": "John Doe",
//                "email": "johndoe@example.com"
//            }
//        """.trimIndent()
//        restTemplate.exchange(url, HttpMethod.POST, HttpEntity(requestBody), String::class.java)
//
//        val url = HTTP_LOCALHOST + port + "/users"
//        val response: ResponseEntity<String> = testRestTemplate.exchange(
//            url,
//            HttpMethod.GET,
//            null,
//            String::class.java
//        )
//
//        assertEquals(HttpStatus.OK, response.statusCode)
//    }
}