package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction
import jakarta.validation.Valid

interface TransactionService {

    fun create(@Valid transaction: Transaction): Transaction

    fun update(transaction: Transaction): Transaction

    fun transfer(transaction: Transaction): Transaction

    fun confirm(transaction: Transaction): Transaction

    fun cancel(transaction: Transaction): Transaction

    fun getTransaction(idTransaction: Long?): Transaction

    fun recoverAll(): List<Transaction>

    fun recoverConfirmed(): List<Transaction>

    fun clear()
}