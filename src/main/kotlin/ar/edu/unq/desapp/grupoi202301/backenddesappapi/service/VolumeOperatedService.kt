package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Volume
import java.time.LocalDateTime

interface VolumeOperatedService {

    fun volumeOperatedByAUserBetweenDates(idUser: Long, firstDate: LocalDateTime, lastDate: LocalDateTime): Volume
}