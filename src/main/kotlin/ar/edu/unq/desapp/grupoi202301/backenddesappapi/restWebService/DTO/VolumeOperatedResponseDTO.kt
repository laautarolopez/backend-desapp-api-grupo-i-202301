package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Volume
import java.time.LocalDateTime

class VolumeOperatedResponseDTO(
    var id: Long?,
    var date: String?,
    var amountUSD: Double?,
    var amountARS: Double?,
    var cryptos: List<CryptoOperatedResponseDTO>?
) {

    companion object {
        fun fromModel(volume: Volume) =
            VolumeOperatedResponseDTO(
                id = volume.idUser,
                date = LocalDateTime.now().toString(),
                amountUSD = volume.amountUSD,
                amountARS = volume.amountARS,
                cryptos = volume.cryptos!!.map { cryptoOperated ->  CryptoOperatedResponseDTO.fromModel(cryptoOperated)}
            )
    }
}