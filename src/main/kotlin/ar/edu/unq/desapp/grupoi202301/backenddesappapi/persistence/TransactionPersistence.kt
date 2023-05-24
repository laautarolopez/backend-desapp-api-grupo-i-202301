package ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionPersistence : JpaRepository<Transaction, Long>