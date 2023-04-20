package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoOperated
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoVolume
import java.time.LocalDateTime

class CryptoVolumeBuilder {
    var timeRequest: LocalDateTime? = null
    var amountUSD: Double? = null
    var amountARS: Double? = null
    var cryptos: List<CryptoOperated>? = null

    fun build(): CryptoVolume {
        var cryptoVolume = CryptoVolume()
        cryptoVolume.timeRequest = this.timeRequest
        cryptoVolume.amountUSD = this.amountUSD
        cryptoVolume.amountARS = this.amountARS
        cryptoVolume.cryptos = this.cryptos
        return cryptoVolume
    }

    fun withTimeRequest(timeRequest: LocalDateTime?): CryptoVolumeBuilder {
        this.timeRequest = timeRequest
        return this
    }

    fun withAmountUSD(amountUSD: Double?): CryptoVolumeBuilder {
        this.amountUSD = amountUSD
        return this
    }

    fun withAmountARS(amountARS: Double?): CryptoVolumeBuilder {
        this.amountARS = amountARS
        return this
    }

    fun withCryptos(cryptos: List<CryptoOperated>?): CryptoVolumeBuilder {
        this.cryptos = cryptos
        return this
    }
}