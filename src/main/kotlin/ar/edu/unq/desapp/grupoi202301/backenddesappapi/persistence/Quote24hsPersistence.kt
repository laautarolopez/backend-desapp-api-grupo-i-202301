package ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoName
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote24hs
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface Quote24hsPersistence: JpaRepository<Quote24hs, Long> {

    override fun findAll(): List<Quote24hs>

    fun findByCryptoName(cryptoName: CryptoName): List<Quote24hs>
}