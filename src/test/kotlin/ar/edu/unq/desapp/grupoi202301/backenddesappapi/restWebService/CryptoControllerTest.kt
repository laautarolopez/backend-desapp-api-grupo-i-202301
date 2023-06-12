package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.CryptoSimpleDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.UserResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.PriceResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
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
class CryptoControllerTest {
    val HTTP_LOCALHOST = "http://localhost:"

    @LocalServerPort
    var port = 0

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun createCrypto() {
        val url = HTTP_LOCALHOST + port + "/cryptos/create"

        val newCrypto = """
            {
              "name": "ALICEUSDT"
            }
        """.trimIndent()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val requestEntity = HttpEntity(newCrypto, headers)

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, String::class.java)

        val cryptoResponse = ObjectMapper().readValue(responseEntity.body, CryptoSimpleDTO::class.java)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(cryptoResponse.id)
        assertEquals(CryptoName.ALICEUSDT, cryptoResponse.name)
    }

    @Test
    fun getPriceOfALICEUSDT() {
        val url = HTTP_LOCALHOST + port + "/cryptos/prices/ALICEUSDT"

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, null, String::class.java)

        val cryptoResponse = ObjectMapper().readValue(responseEntity.body, PriceResponse::class.java)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals("ALICEUSDT", cryptoResponse.cryptoName)
        assertTrue(cryptoResponse.price >= 0.0)
        assertTrue(cryptoResponse.time != "")
    }

    @Test
    fun badRequestInGetPrices() {
        val url = HTTP_LOCALHOST + port + "/cryptos/prices/errorName"

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, null, String::class.java)

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
    }
}