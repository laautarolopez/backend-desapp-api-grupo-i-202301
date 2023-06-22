package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import jakarta.validation.Valid

interface UserService {

    fun create(@Valid user: User): User

    fun getUser(idUser: Long?): User

    fun update(user: User): User

    fun getByEmail(email: String): User?

    fun recoverAll(): List<User>

    fun clear()
}