package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.BinanceServerException
import org.springframework.stereotype.Service
import retrofit2.Call

data class DolarBlueResponse(val name: String, val price: Double)

data class DolarSiResponse(val casa: Casa)

data class Casa(val nombre: String, val compra: String, val venta: String)

interface DolarResponseInt {
    fun getPrice(): DolarBlueResponse
}

@Service
class DolarResponse : DolarResponseInt {
    override fun getPrice(): DolarBlueResponse {
        val dolarService = DolarService.create()
        val call = dolarService.getPrice("valoresprincipales")

        return executeCall(call)
    }

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