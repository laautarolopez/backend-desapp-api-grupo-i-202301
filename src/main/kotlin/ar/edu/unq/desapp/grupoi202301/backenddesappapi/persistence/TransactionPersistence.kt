package ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TransactionPersistence : CrudRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM transactions t WHERE t.status = :status AND t.seller_id = :idUser OR t.buyer_id = :idUser " +
            "AND t.date BETWEEN :firstDate AND :lastDate", nativeQuery = true)
    fun getConfirmedTransactionsFromUserBetweenTwoDates(@Param("idUser")idUser: Long,
                                                        @Param("firstDate")firstDate: LocalDateTime,
                                                        @Param("lastDate")lastDate: LocalDateTime,
                                                        @Param("status")status: Int): List<Transaction>
}