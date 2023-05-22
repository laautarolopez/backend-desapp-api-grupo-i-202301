package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.apiBinance

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.BinanceResponseException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.BinanceServerException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.ErrorBinanceResponse
import com.google.gson.Gson
import retrofit2.Call

data class DolarBlueResponse(val name: String, val price: Double)

data class DolarSiResponse(val casa: Casa)

data class Casa(val nombre: String, val compra: String, val venta: String)

class DolarResponse {
    fun getPrice(): DolarBlueResponse {
        val dolarService = DolarService.create()
        val call = dolarService.getPrice("valoresprincipales")

        return executeCall(call)
    }

//    private fun executeCall(call: Call<List<Casa>>): DolarBlueResponse {
//        var response = call.execute()
//        if(response.isSuccessful) {
//            val casaDolar = response.body()!!.find {
//                casa -> casa.nombre == "Dolar Blue"
//            }
//            return DolarBlueResponse(casaDolar!!.venta)
//        } else {
//            try {
//                val gson = Gson()
//                val error: ErrorBinanceResponse = gson.fromJson(response.errorBody()?.charStream(), ErrorBinanceResponse::class.java)
//                throw BinanceResponseException("cryptoname", error.msg)
//            } catch (e: com.google.gson.JsonSyntaxException) {
//                throw BinanceServerException("The server does not respond.")
//            }
//        }
//    }

    private fun executeCall(call: Call<Array<DolarSiResponse>>): DolarBlueResponse {
        var response = call.execute()
        if(response.isSuccessful) {
            println(response.body()!!)
            val casaDolar = response.body()!!.find {
                elem -> elem.casa.nombre == "Dolar Blue"
            }
            return DolarBlueResponse(casaDolar!!.casa.nombre, this.toDouble(casaDolar!!.casa.venta))
        } else {
            throw BinanceServerException("The server does not respond.")
        }
    }

    private fun toDouble(number: String): Double {
        return number.replace(",", ".").toDouble()
    }
}