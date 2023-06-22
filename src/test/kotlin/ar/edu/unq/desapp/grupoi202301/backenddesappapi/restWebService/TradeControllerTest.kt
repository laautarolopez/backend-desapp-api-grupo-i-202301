package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.OperationType
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.TradeResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.ErrorDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.BinanceResponseInt
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.PriceResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import java.time.LocalDateTime

@RunWith(SpringRunner::class)
@ExtendWith(MockitoExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TradeControllerTest {
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
    fun createTrade() {
        val token = loginUser("jorgesanchez@gmail.com")

        val url = HTTP_LOCALHOST + port + "/trades/create"

        val newTrade = """
            {
                "idCrypto": 1,
                "quantity": 100.0,
                "idUser": 1,
                "operation": "SALE"
            }
        """.trimIndent()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Authorization", token)

        val requestEntity = HttpEntity(newTrade, headers)

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, String::class.java)

        val tradeResponse = ObjectMapper().readValue(responseEntity.body, TradeResponseDTO::class.java)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(tradeResponse.id)
        assertEquals(CryptoName.ALICEUSDT, tradeResponse.crypto.name)
        assertEquals(100.0, tradeResponse.quantity)
        assertEquals("Lautaro", tradeResponse.user!!.name)
        assertEquals(OperationType.SALE, tradeResponse.operation)
        assertTrue(tradeResponse.active!!)
    }

    @Test
    fun getTrade() {
        val token = loginUser("lautarosanchez@gmail.com")

        val createUrl = HTTP_LOCALHOST + port + "/trades/create"
        val newTrade = """
            {
                "idCrypto": 1,
                "quantity": 50.0,
                "idUser": 2,
                "operation": "BUY"
            }
        """.trimIndent()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Authorization", token)
        val requestEntity = HttpEntity(newTrade, headers)
        val createResponseEntity = testRestTemplate.exchange(createUrl, HttpMethod.POST, requestEntity, String::class.java)
        val tradeCreateResponse = ObjectMapper().readValue(createResponseEntity.body, TradeResponseDTO::class.java)

        /* ---- */

        val url = HTTP_LOCALHOST + port + "/trades/" + tradeCreateResponse.id

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, null, String::class.java)

        val tradeResponse = ObjectMapper().readValue(responseEntity.body, TradeResponseDTO::class.java)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals(tradeCreateResponse.id, tradeResponse.id)
        assertEquals(CryptoName.ALICEUSDT, tradeResponse.crypto.name)
        assertEquals(50.0, tradeResponse.quantity)
        assertEquals("Fabricio", tradeResponse.user!!.name)
        assertEquals(OperationType.BUY, tradeResponse.operation)
        assertTrue(tradeResponse.active!!)
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
    fun getNonExistentTrade() {
        val url = HTTP_LOCALHOST + port + "/trades/9999"

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, null, String::class.java)

        val errorResponse = ObjectMapper().readValue(responseEntity.body, ErrorDTO::class.java)

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        assertEquals("trade", errorResponse.attribute)
        assertEquals("The trade does not exist.", errorResponse.message)
    }
}