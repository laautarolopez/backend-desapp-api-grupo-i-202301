package ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TradePersistence : JpaRepository<Trade, Long> {
    override fun findAll(): List<Trade>
}