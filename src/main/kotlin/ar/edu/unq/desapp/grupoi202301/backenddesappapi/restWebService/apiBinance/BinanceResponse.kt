package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.BinanceResponseException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.BinanceServerException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.ErrorBinanceResponse
import com.google.gson.Gson
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter
import retrofit2.Call
import java.time.LocalDateTime

data class PriceResponse(val cryptoName: String, val price: Double, val time: String)

class BinanceResponse {
    fun getPrice(cryptoName: String): PriceResponse {
        val binanceService = BinanceService.create()
        val call = binanceService.getPrice(cryptoName)

        return executeCall(cryptoName, call)
    }

    private fun executeCall(cryptoName: String, call: Call<PriceResponse>): PriceResponse {
        var response = call.execute()
        if(response.isSuccessful) {
            val time = LocalDateTime.now().toString()
            return PriceResponse(cryptoName, response.body()!!.price, time)
        } else {
            try {
                val gson = Gson()
                val error: ErrorBinanceResponse = gson.fromJson(response.errorBody()?.charStream(), ErrorBinanceResponse::class.java)
                throw BinanceResponseException(cryptoName, error.msg)
            } catch (e: com.google.gson.JsonSyntaxException) {
                throw BinanceServerException("The server does not respond.")
            }
        }
    }

    fun getPrices(): List<PriceResponse> {
        var list: List<PriceResponse> = listOf()
        val binanceService = BinanceService.create()
        var call: Call<PriceResponse>
        var body: PriceResponse?
        var priceResponse: PriceResponse
        CryptoName.values().forEach { cryptoName ->
            call = binanceService.getPrice(cryptoName.toString())
            priceResponse = executeCall(cryptoName.toString(), call)
            list += priceResponse
        }
        return list
    }
}