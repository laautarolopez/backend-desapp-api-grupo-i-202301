package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.lang.RuntimeException

@Entity
class Adress() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private var id: Long? = null
    private var adress: String? = null

    constructor(adress: String) : this() {
        changeAdress(adress)
    }

    fun adress(): String = this.adress!!

    fun changeAdress(newAdress: String) {
        if(isLessThan(newAdress,10) || isGreaterThan(newAdress,30)) {
            throw RuntimeException("The address must be between 10 and 30 characters long.")
        }
        this.adress = newAdress
    }

    private fun isLessThan(adress: String, amount: Int) : Boolean = adress.length < amount

    private fun isGreaterThan(adress: String, amount: Int) : Boolean = adress.length > amount
}