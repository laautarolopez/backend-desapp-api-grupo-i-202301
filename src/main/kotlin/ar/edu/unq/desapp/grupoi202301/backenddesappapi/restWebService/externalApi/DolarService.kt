package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DolarService {
    @GET("api.php")
    fun getPrice(@Query("type") type: String): Call<Array<DolarSiResponse>>

    companion object {
        fun create(): DolarService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.dolarsi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(DolarService::class.java)
        }
    }
}