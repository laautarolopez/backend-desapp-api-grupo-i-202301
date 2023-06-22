package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.TransactionStatus
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.TradeResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.TransactionResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.ErrorDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.BinanceResponseInt
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.PriceResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
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
class TransactionControllerTest {
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

    private fun createTrade(): Long {
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

        return tradeResponse.id!!
    }

    @Test
    fun createTransaction() {
        val token = loginUser("lautarosanchez@gmail.com")
        val idTrade = createTrade()

        val url = HTTP_LOCALHOST + port + "/transactions/create"

        val newTransaction = """
            {
                "idUserRequested": 2,
                "idTrade": """ + idTrade + """
            }
        """.trimIndent()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Authorization", token)

        val requestEntity = HttpEntity(newTransaction, headers)

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, String::class.java)

        val transactionResponse = ObjectMapper().readValue(responseEntity.body, TransactionResponseDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertNotNull(transactionResponse.id)
        Assertions.assertEquals(idTrade, transactionResponse.trade!!.id)
        Assertions.assertEquals("Fabricio", transactionResponse.buyer!!.name)
    }

    @Test
    fun getTransaction() {
        val token = loginUser("lautarosanchez@gmail.com")
        val idTrade = createTrade()
        val createUrl = HTTP_LOCALHOST + port + "/transactions/create"
        val newTransaction = """
            {
                "idUserRequested": 2,
                "idTrade": """ + idTrade + """
            }
        """.trimIndent()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Authorization", token)
        val requestEntity = HttpEntity(newTransaction, headers)
        val createResponseEntity = testRestTemplate.exchange(createUrl, HttpMethod.POST, requestEntity, String::class.java)
        val transactionCreateResponse = ObjectMapper().readValue(createResponseEntity.body, TransactionResponseDTO::class.java)

        /* ---- */

        val url = HTTP_LOCALHOST + port + "/transactions/" + transactionCreateResponse.id

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, null, String::class.java)

        val transactionResponse = ObjectMapper().readValue(responseEntity.body, TransactionResponseDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertEquals(transactionCreateResponse.id, transactionResponse.id)
        Assertions.assertEquals(idTrade, transactionResponse.trade!!.id)
        Assertions.assertEquals("Fabricio", transactionResponse.buyer!!.name)
        Assertions.assertEquals(TransactionStatus.CREATED, transactionResponse.status)
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
    fun getNonExistentTransaction() {
        val url = HTTP_LOCALHOST + port + "/transactions/9999"

        val responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, null, String::class.java)

        val errorResponse = ObjectMapper().readValue(responseEntity.body, ErrorDTO::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        Assertions.assertEquals("transaction", errorResponse.attribute)
        Assertions.assertEquals("The transaction does not exist.", errorResponse.message)
    }
}