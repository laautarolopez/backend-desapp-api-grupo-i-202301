package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.CryptoPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.TradePersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.UserPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TradeService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class TradeServiceImp(
    private val tradePersistence: TradePersistence,
    private val cryptoService: CryptoService,
    private val userService: UserService
    ) : TradeService {

    override fun create(trade: Trade): Trade {
        val crypto = cryptoService.recove(trade.crypto!!.id!!.toInt())
        val user = userService.recove(trade.user!!.id!!.toInt())
        // TODO: terminar de validar crypto y user.
        // TODO: instanciar
        return tradePersistence.save(trade)
    }

    override fun update(trade: Trade): Trade {
        return this.create(trade)
    }

    override fun recove(tradeId: Int): Trade {
        val trade = tradePersistence.findByIdOrNull(tradeId.toLong())
        if (trade == null) {
            throw RuntimeException("The id does not exist.")
        }
        return trade
    }

    override fun recoverAll(): List<Trade> {
        return tradePersistence.findAll()
    }

    override fun recoverActives(idUser: Long): List<Trade> {
        val trades = this.recoverAll()
        var tradesActives = listOf<Trade>()

        trades.map { trade ->
            if (trade.user!!.id == idUser && trade.isActive!!) {
                tradesActives += trade
            }
        }
        return tradesActives
    }

    override fun clear() {
        tradePersistence.deleteAll()
    }
}