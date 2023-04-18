package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.lang.RuntimeException

@Entity
class Password() {
    @Id
    private var id: Long? = null
    private var password: String? = null

    constructor(password: String) : this() {
        changePassword(password)
    }

    fun password(): String = this.password!!

    fun changePassword(newPassword: String) {
        if (!isValid(newPassword)) {
        throw RuntimeException("Must contain at least 1 lower case, 1 upper case, 1 special character and at least 6 digits.")
        }
        this.password = newPassword
    }

    fun isValid(password: String): Boolean {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*().,<>{}[\\]<>?_=+\\-|;:\\'\\\"\\/]])(?!.*\\s).{8,20}$".toRegex())
    }

    private fun hasAtLeastOneLowercase(s: String): Boolean {
        return s.chars().anyMatch { Character.isLowerCase(it) }
    }

    private fun hasAtLeastOneUppercase(s: String): Boolean {
        return s.chars().anyMatch { Character.isUpperCase(it) }
    }

}