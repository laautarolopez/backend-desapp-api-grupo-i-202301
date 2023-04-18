package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.lang.RuntimeException

@Entity
class Email() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private var id: Long? = null
    private var email: String? = null

    constructor(email: String) : this() {
        changeEmail(email)
        // "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    }

    fun email(): String = this.email!!

    fun changeEmail(newEmail: String) {
        if(!newEmail.contains("@")) {
            throw RuntimeException("A valid email address must be used.")
        }
        this.email = newEmail
    }

}