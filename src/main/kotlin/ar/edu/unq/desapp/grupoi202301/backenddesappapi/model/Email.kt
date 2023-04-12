package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.lang.RuntimeException

@Entity
class Email() {
    @Id
    private var id: Long? = null
    private var email: String? = null

    constructor(email: String) : this() {
        changeEmail(email)
    }

    fun email(): String = this.email!!

    fun changeEmail(newEmail: String) {
        if(!newEmail.contains("@")) {
            throw RuntimeException("A valid email address must be used.")
        }
        this.email = newEmail
    }

}