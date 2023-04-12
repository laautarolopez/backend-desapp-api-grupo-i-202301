package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

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
        this.password = newPassword
    }
}