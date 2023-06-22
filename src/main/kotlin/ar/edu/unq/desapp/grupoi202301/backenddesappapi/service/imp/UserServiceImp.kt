package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.UserPersistence
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.exception.UserNonExistent
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

    override fun getUser(idUser: Long?): User {
        validateId(idUser)
        try {
            return userPersistence.getReferenceById(idUser!!)
        } catch(e: RuntimeException) {
            throw UserNonExistent()
        }
    }

    override fun update(user: User): User {
        this.getUser(user.id)
        return userPersistence.save(user)
    }

    private fun validateId(idUser: Long?) {
        if(idUser == null) {
            throw UserNonExistent()
        }
    }

    override fun getByEmail(email: String): User? {
        return userPersistence.findByEmail(email)
    }

    override fun recoverAll(): List<User> {
        return userPersistence.findAll()
    }

    override fun clear() {
        userPersistence.deleteAll()
    }
}