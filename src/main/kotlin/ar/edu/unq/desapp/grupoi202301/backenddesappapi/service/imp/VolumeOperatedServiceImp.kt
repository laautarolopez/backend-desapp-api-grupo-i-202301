package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoOperated
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.TransactionStatus
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Volume
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.*
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime

@Service
@Validated
@Transactional
class VolumeOperatedServiceImp(
    @Autowired
    private val transactionService: TransactionService,
    @Autowired
    private val userService: UserService,
) : VolumeOperatedService {

    override fun volumeOperatedByAUserBetweenDates(idUser: Long, firstDate: String, lastDate: String): Volume {
        validateYRecoverUserRequest(idUser)
        val date1 = LocalDateTime.parse(firstDate)
        val date2 = LocalDateTime.parse(lastDate)
        val requestDateTime = LocalDateTime.now()
        var totalAmountUSD: Double = 0.0
        var totalAmountARS: Double = 0.0
        val transactionsConfirmed = transactionService.recoverConfirmedTransactionsFromUserBetweenTwoDates(idUser, date1, date2, TransactionStatus.CONFIRMED)

        transactionsConfirmed.forEach { transaction -> totalAmountUSD += transaction.getAmountUSD()!! }
        transactionsConfirmed.forEach { transaction -> totalAmountARS += transaction.trade!!.amountARS!! }

        val cryptoOperateds = transactionsConfirmed.map { transaction -> CryptoOperated(transaction.trade!!.crypto!!.name!!,
                                                                  transaction.trade!!.quantity!!,
                                                                  transaction.trade!!.crypto!!.price!!,
                                                                  transaction.trade!!.amountARS!!)}

        var volumeResponse = Volume(idUser, requestDateTime, totalAmountUSD, totalAmountARS, cryptoOperateds)
        return volumeResponse
    }

    private fun validateYRecoverUserRequest(idUser: Long?): User {
        return userService.getUser(idUser)
    }
}