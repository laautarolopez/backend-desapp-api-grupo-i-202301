package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import jakarta.validation.Valid

interface TradeService {

    fun create(@Valid trade: Trade): Trade

    fun recoverAll(): List<Trade>

    fun recoverActives(idUser: Long): List<Trade>

    fun clear()
}