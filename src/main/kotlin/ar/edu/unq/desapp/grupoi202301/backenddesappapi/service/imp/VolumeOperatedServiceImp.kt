package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.CryptoOperated
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Volume
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.*
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        val user = validateYRecoverUserRequest(idUser)
        validateDates(firstDate, lastDate)
        val requestDateTime = LocalDateTime.now()
        var totalAmountUSD: Double = 0.0
        var totalAmountARS: Double = 0.0
        val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val date1 = LocalDateTime.parse(firstDate, formatter)
        val date2 = LocalDateTime.parse(lastDate, formatter)
        val transactionsConfirmed = transactionService.recoverConfirmedTransactionsFromUserBetweenTwoDates(user.id!!, date1, date2)

        transactionsConfirmed.forEach { transaction -> totalAmountUSD += transaction.getAmountUSD()!! }
        transactionsConfirmed.forEach { transaction -> totalAmountARS += transaction.trade!!.amountARS!! }

        val cryptoOperateds = transactionsConfirmed.map { transaction -> CryptoOperated(
                                                                  transaction.trade!!.crypto!!.id!!,
                                                                  transaction.trade!!.crypto!!.name!!,
                                                                  transaction.trade!!.quantity!!,
                                                                  transaction.trade!!.crypto!!.price!!,
                                                                  transaction.trade!!.amountARS!!)}

        var volumeResponse = Volume(user.id!!.toLong(), requestDateTime, totalAmountUSD, totalAmountARS, cryptoOperateds)
        return volumeResponse
    }

    private fun validateYRecoverUserRequest(idUser: Long?): User {
        return userService.getUser(idUser)
    }

    private fun validateDates(date1: String?, date2: String?) {
        if (date1 == "" || date2 == "") {
            throw RuntimeException("At least one date is invalid.")
        }
    }
}