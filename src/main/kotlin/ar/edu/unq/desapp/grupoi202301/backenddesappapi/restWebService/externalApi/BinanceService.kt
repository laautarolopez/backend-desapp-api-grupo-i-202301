package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BinanceService {
    @GET("price")
    fun getPrice(@Query("symbol") cryptoName: String): Call<PriceResponse>

    companion object {
        fun create(): BinanceService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.binance.com/api/v3/ticker/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(BinanceService::class.java)
        }
    }
}