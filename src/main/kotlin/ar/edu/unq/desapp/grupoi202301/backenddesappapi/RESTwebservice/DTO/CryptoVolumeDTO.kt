package ar.edu.unq.desapp.grupoi202301.backenddesappapi.RESTwebservice.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoOperated
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoVolume
import java.time.LocalDateTime

class CryptoVolumeDTO(
    var id: Long?,
    var timeRequest: LocalDateTime?,
    var amountUSD: Double?,
    var amountARS: Double?,
    var cryptos: List<CryptoOperated>?
) {

    fun toModel(): CryptoVolume {
        val crypto = CryptoVolume()
        crypto.id = this.id
        crypto.timeRequest = this.timeRequest
        crypto.amountUSD = this.amountUSD
        crypto.amountARS = this.amountARS
        crypto.cryptos = this.cryptos
        return crypto
    }

    companion object {
        fun fromModel(crypto: CryptoVolume) =
            CryptoVolumeDTO(
                id = crypto.id,
                timeRequest = crypto.timeRequest,
                amountUSD = crypto.amountUSD,
                amountARS = crypto.amountARS,
                cryptos = crypto.cryptos
            )
    }
}