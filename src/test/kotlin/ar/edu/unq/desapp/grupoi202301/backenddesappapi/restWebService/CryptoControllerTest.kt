package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.CryptoSimpleDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.BinanceResponseInt
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.PriceResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDateTime

@RunWith(SpringRunner::class)
@ExtendWith(MockitoExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CryptoControllerTest {
    val HTTP_LOCALHOST = "http://localhost:"

    @LocalServerPort
    var port = 0

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @MockBean
    lateinit var binanceResponse: BinanceResponseInt

    @BeforeEach
    fun setup() {
        fun price(cryptoName: String) = PriceResponse(cryptoName, 1.123, LocalDateTime.now().toString())
        val list = listOf(price("BTCUSDT"), price("AAVEUSDT"), price("ALICEUSDT"), price("ETHUSDT"))

        CryptoName.values().forEach {
                name -> Mockito.`when`(binanceResponse.getPrice(name.toString())).thenReturn(price(name.toString()))
        }

        Mockito.`when`(binanceResponse.getPrices()).thenReturn(list)
    }

    @Test
    fun createCrypto() {
        val token = loginUser("admin@admin.com")
        val url = HTTP_LOCALHOST + port + "/cryptos/create"

        val newCrypto = """
            {
              "name": "ALICEUSDT"
            }
        """.trimIndent()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Authorization", token)

        val requestEntity = HttpEntity(newCrypto, headers)

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, String::class.java)

        val cryptoResponse = ObjectMapper().readValue(responseEntity.body, CryptoSimpleDTO::class.java)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(cryptoResponse.id)
        assertEquals(CryptoName.ALICEUSDT, cryptoResponse.name)
    }

    private fun loginUser(email: String): String {
        val loginUrl = HTTP_LOCALHOST + port + "/login"

        val userRequested = """
            {
                "email": "${email}",
                "password": "Password@1234"         
            }
        """.trimIndent()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(userRequested, headers)
        val loginResponse = testRestTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, String::class.java)

        val token = "Bearer " + loginResponse.headers.get("Authorization")!!.get(0)

        return token
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