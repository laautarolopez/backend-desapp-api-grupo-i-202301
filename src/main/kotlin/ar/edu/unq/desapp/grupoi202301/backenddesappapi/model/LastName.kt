package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.lang.RuntimeException

@Entity
class LastName() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private var id: Long? = null
    private var lastName: String? = null

    constructor(lastName: String) : this() {
        changeLastName(lastName)
    }

    fun lastName(): String = this.lastName!!

    fun changeLastName(newLastName: String) {
        if(isLessThan(newLastName, 3) || isGreaterThan(newLastName, 30)) {
            throw RuntimeException("The last name must be between 3 and 30 characters long.")
        }
        this.lastName = newLastName
    }

    private fun isLessThan(newLastName: String, amount: Int) : Boolean = newLastName.length < amount

    private fun isGreaterThan(newLastName: String, amount: Int) : Boolean = newLastName.length > amount
}