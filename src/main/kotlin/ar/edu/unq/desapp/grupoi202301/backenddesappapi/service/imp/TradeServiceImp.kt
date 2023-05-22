package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Crypto
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.CryptoPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.TradePersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.UserPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TradeService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class TradeServiceImp(
    private val tradePersistence: TradePersistence,
    @Autowired
    private val cryptoService: CryptoService,
    @Autowired
    private val userService: UserService
    ) : TradeService {

    override fun create(trade: Trade): Trade {
        val crypto = cryptoService.getCrypto(trade.crypto!!.id!!)
        val user = userService.getUser(trade.user!!.id!!)
        trade.crypto = crypto
        trade.user = user
        return tradePersistence.save(trade)
    }

    override fun update(trade: Trade): Trade {
        return this.create(trade)
    }

    override fun getTrade(idTrade: Long): Trade {
        val trade = tradePersistence.findByIdOrNull(idTrade)
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