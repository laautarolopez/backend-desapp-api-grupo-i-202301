package ar.edu.unq.desapp.grupoi202301.backenddesappapi.RESTwebservice.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoOperated
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.CryptoEmptyException

class CryptoOperatedDTO(
    var id: Long?,
    var cryptoName: String?,
    var quantity: Double?,
    var price: Double?,
    var amountARS: Double? = null
) {

    fun toModel(): CryptoOperated {
        val crypto = CryptoOperated()
        crypto.id = this.id
        crypto.cryptoName = this.verifyCrypto(this.cryptoName)
        crypto.quantity = this.quantity
        crypto.price = this.price
        crypto.amountARS = this.amountARS
        return crypto
    }

    companion object {
        fun fromModel(crypto: CryptoOperated) =
            CryptoOperatedDTO(
                id = crypto.id,
                cryptoName = crypto.cryptoName.toString(),
                quantity = crypto.quantity,
                price = crypto.price,
                amountARS = crypto.amountARS,
            )
    }

    private fun verifyCrypto(crypto: String?): CryptoName {
        var newCrypto: CryptoName
        when(crypto) {
            "ALICEUSDT" -> newCrypto = CryptoName.ALICEUSDT
            "MATICUSDT" -> newCrypto = CryptoName.MATICUSDT
            "AXSUSDT" -> newCrypto = CryptoName.AXSUSDT
            "AAVEUSDT" -> newCrypto = CryptoName.AAVEUSDT
            "ATOMUSDT" -> newCrypto = CryptoName.ATOMUSDT
            "NEOUSDT" -> newCrypto = CryptoName.NEOUSDT
            "DOTUSDT" -> newCrypto = CryptoName.DOTUSDT
            "ETHUSDT" -> newCrypto = CryptoName.ETHUSDT
            "CAKEUSDT" -> newCrypto = CryptoName.CAKEUSDT
            "BTCUSDT" -> newCrypto = CryptoName.BTCUSDT
            "BNBUSDT" -> newCrypto = CryptoName.BNBUSDT
            "ADAUSDT" -> newCrypto = CryptoName.ADAUSDT
            "TRXUSDT" -> newCrypto = CryptoName.TRXUSDT
            "AUDIOUSDT" -> newCrypto = CryptoName.AUDIOUSDT
            else -> {
                throw CryptoEmptyException()
            }
        }
        return newCrypto
    }
}