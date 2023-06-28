package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote24hs
import jakarta.validation.Valid

interface Quote24hsService {

    fun create(@Valid quote24hs: Quote24hs): Quote24hs

    fun update(@Valid quote24hs: Quote24hs): Quote24hs

    fun delete(quote24hs: Quote24hs)

    fun findByCryptoName(cryptoName: CryptoName): List<Quote24hs>

    fun getQuote24hs(idQuote: Long?): Quote24hs

    fun recoverAll(): List<Quote24hs>

    fun clear()
}