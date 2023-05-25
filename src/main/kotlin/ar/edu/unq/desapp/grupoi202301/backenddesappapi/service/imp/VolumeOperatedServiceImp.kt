package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

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
        val user = validateYRecoverUserRequest(idUser)
        val date1 = LocalDateTime.parse(firstDate)
        val date2 = LocalDateTime.parse(lastDate)
        val requestDateTime = LocalDateTime.now()
        var totalAmountUSD: Double
        var totalAmountARS: Double
        val transactionsConfirmed = transactionService.recoverConfirmed()


        TODO("Not yet implemented")

    }

    private fun validateYRecoverUserRequest(idUser: Long?): User {
        return userService.getUser(idUser)
    }
}