package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.lang.RuntimeException

@Entity
class CVUMercadoPago() {
    @Id
    private var id: Long? = null
    private var cvuMercadoPago: String? = null

    constructor(cvuMercadoPago: String) : this() {
        changeCVUMercadoPago(cvuMercadoPago)
    }

    fun cvuMercadoPago(): String = this.cvuMercadoPago!!

    fun changeCVUMercadoPago(newCVU: String) {
        if(!isNumeric(newCVU)) {
            throw RuntimeException("The CVU must only have digits.")
        }
        else if(isDifferentFrom(newCVU,22)) {
            throw RuntimeException("The CVU must have 22 digits.")
        }

        this.cvuMercadoPago = newCVU
    }

    private fun isNumeric(s: String): Boolean {
        return s.chars().allMatch { Character.isDigit(it) }
    }

    private fun isDifferentFrom (cvu: String, cantidad: Int) : Boolean = cvu.length != cantidad
}