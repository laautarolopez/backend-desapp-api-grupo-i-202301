package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Volume

interface VolumeOperatedService {

    fun volumeOperatedByAUserBetweenDates(idUser: Long, firstDate: String, lastDate: String): Volume
}