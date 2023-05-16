package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class PriceResponse(val symbol: String, val price: Double)

class BinanceResponse {
    fun getPrice(cryptoName: CryptoName): Double {
        val url = "https://api.binance.com/api/v3/ticker/price?symbol=$cryptoName"
        val binanceService = BinanceService.create()
        val call = binanceService.getPrice(cryptoName)
        return call.execute().body()!!.price

        call.enqueue(object : Callback<PriceResponse> {
            override fun onResponse(call: Call<PriceResponse>, response: Response<PriceResponse>) {
                if (response.isSuccessful) {
                    val price = response.body()
                    price!!.price
                } else {
                    response.code()
                }
            }

            override fun onFailure(call: Call<PriceResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return 0.0
    }
}