package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.OperationType
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.TradeResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.ErrorDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TradeControllerTest {
    val HTTP_LOCALHOST = "http://localhost:"

    @LocalServerPort
    var port = 0

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun createTrade() {
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