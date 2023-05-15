package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Trade
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.TradePersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TradeService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class TradeServiceImp(
    private val tradePersistence: TradePersistence,
    private val cryptoService: CryptoService,
    private val userService: UserService,
    ) : TradeService {

    override fun create(trade: Trade): Trade {
        cryptoService.create(trade.crypto!!)
        userService.create(trade.user!!)
        return tradePersistence.save(trade)
        // TODO: Eliminar persistencia de Crypto y Usuario
    }
}