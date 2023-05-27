package ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.TransactionStatus
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TransactionPersistence : CrudRepository<Transaction, Long> {

    @Query(value= "SELECT t FROM transaction t WHERE t.date BETWEEN ?2 AND ?3" +
            "AND t.status = ?4 " +
            "AND t.seller.id = ?1 OR t.buyer.id = ?1", nativeQuery = true)
    fun getConfirmedTransactionsFromUserBetweenTwoDates(idUser: Long?, firstDate: LocalDateTime, lastDate: LocalDateTime, status: TransactionStatus?): List<Transaction>
}