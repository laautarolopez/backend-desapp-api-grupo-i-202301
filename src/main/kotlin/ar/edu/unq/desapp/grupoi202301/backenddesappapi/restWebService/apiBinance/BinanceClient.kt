package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente de nuestra API REST
 */
//object BinanceClient {
//    private const val API_URL = "https://reqres.in/"
//
//    // Creamos una instancia de Retrofit con las llamadas a la API
//    fun getInstance(): ReqresRest {
//        return Retrofit.Builder().baseUrl(API_URL)
//                // Nuestro conversor de JSON
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(ReqresRest::class.java)
//    }
//}