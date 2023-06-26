package ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuotePersistence: JpaRepository<Quote, Long> {

    fun findByCryptoName(cryptoName: CryptoName): Quote
}