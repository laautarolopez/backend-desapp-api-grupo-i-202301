package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.lang.RuntimeException

@Entity
class LastName() {
    @Id
    private var id: Long? = null
    private var lastName: String? = null

    constructor(lastName: String) : this() {
        changeLastName(lastName)
    }

    fun lastName(): String = this.lastName!!

    fun changeLastName(newLastName: String) {
        if(esMenorA(newLastName, 3) || esMayorA(newLastName, 30)) {
            throw RuntimeException("The last name must be between 3 and 30 characters long.")
        }
        this.lastName = newLastName
    }

    private fun esMenorA(newLastName: String, cantidad: Int) : Boolean = newLastName.length < cantidad

    private fun esMayorA(newLastName: String, cantidad: Int) : Boolean = newLastName.length > cantidad
}