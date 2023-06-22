package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoOperated
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Volume
import java.time.LocalDateTime

class VolumeBuilder {
    var idUser: Long? = null
    var date: LocalDateTime? = null
    var amountUSD: Double? = null
    var amountARS: Double? = null
    var cryptos: List<CryptoOperated>? = null

    fun build(): Volume {
        var volume = Volume()
        volume.idUser = this.idUser
        volume.date = this.date
        volume.amountUSD = this.amountUSD
        volume.amountARS = this.amountARS
        volume.cryptos = this.cryptos
        return volume
    }

    fun withIdUser(id: Long?): VolumeBuilder {
        this.idUser = id
        return this
    }

    fun withDate(date: LocalDateTime?): VolumeBuilder {
        this.date = date
        return this
    }

    fun withAmountUSD(amount: Double?): VolumeBuilder {
        this.amountUSD = amount
        return this
    }

    fun withAmountARS(amount: Double?): VolumeBuilder {
        this.amountARS = amount
        return this
    }

    fun withCryptos(cryptos: List<CryptoOperated>): VolumeBuilder {
        this.cryptos = cryptos
        return this
    }
}