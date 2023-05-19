package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction
import jakarta.validation.Valid

interface TransactionService {

    fun create(@Valid transaction: Transaction): Transaction

    fun clear()
}