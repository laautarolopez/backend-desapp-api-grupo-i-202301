package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote
import jakarta.validation.Valid

interface QuoteService {

    fun create(@Valid quote: Quote): Quote

    fun update(@Valid trade: Quote): Quote

    fun getQuote(idQuote: Long?): Quote

    fun getQuotesList(): String

    fun updateQuotesList()

    fun clear()
}