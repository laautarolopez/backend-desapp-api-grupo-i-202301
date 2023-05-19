package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.UserPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class UserServiceImp(
    private val userPersistence: UserPersistence
    ) : UserService {

    override fun create(user: User): User {
        return userPersistence.save(user)
    }

    override fun recoverAll(): List<User> {
        return userPersistence.findAll()
    }

    override fun clear() {
        userPersistence.deleteAll()
    }
}