package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.lang.RuntimeException

@Entity
class Name() {
    @Id
    private var id: Long? = null
    private var name: String? = null

    constructor(name: String) : this() {
        changeName(name)
    }

    fun name(): String = this.name!!

    fun changeName(newName: String) {
        if(esMenorA(newName, 3) || esMayorA(newName, 30)) {
            throw RuntimeException("The name must be between 3 and 30 characters long.")
        }
        this.name = newName
    }

    private fun esMenorA(newName: String, cantidad: Int) : Boolean = newName.length < cantidad

    private fun esMayorA(newName: String, cantidad: Int) : Boolean = newName.length > cantidad
}