package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.TradeNonExistentException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.TradePersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.DolarResponseInt
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TradeService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import jakarta.transaction.Transactional
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
    private val userService: UserService,
    @Autowired
    private val dolarResponse: DolarResponseInt
    ) : TradeService {

    override fun create(trade: Trade): Trade {
        recoverCrypto(trade)
        recoverUser(trade)
        updateAmountARSInCreate(trade)
        return tradePersistence.save(trade)
    }

    private fun updateAmountARSInCreate(trade: Trade) {
        val price = trade.quantity!! * trade.cryptoPrice!!
        val dolarBlue = dolarResponse.getPrice()
        trade.amountARS = price * dolarBlue.price
    }

    private fun recoverCrypto(trade: Trade) {
        val crypto = cryptoService.getCrypto(trade.crypto!!.id)
        trade.crypto = crypto
        trade.cryptoPrice = crypto.price
    }

    private fun recoverUser(trade: Trade) {
        trade.user = userService.getUser(trade.user!!.id)
    }

    override fun update(trade: Trade): Trade {
        getTrade(trade.id)
        return tradePersistence.save(trade)
    }

    override fun getTrade(idTrade: Long?): Trade {
        validateId(idTrade)
        try {
            val trade = tradePersistence.getReferenceById(idTrade!!)
            updateAmountARSInCreate(trade)
            return trade
        } catch(e: RuntimeException) {
            throw TradeNonExistentException("trade", "The trade does not exist.")
        }
    }

    private fun validateId(idTrade: Long?) {
        if(idTrade == null) {
            throw TradeNonExistentException("trade", "The trade does not exist.")
        }
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