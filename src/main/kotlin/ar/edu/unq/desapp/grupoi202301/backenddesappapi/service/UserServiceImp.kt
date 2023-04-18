package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.UserDAO
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class UserServiceImp(
    private val userDAO: UserDAO
    ) : UserService {

    override fun create(user: User): User {
        return userDAO.save(user)
    }
}