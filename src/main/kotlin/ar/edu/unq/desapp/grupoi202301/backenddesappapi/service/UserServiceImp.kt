package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.persistence.UserDAO
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class UserServiceImp(
    @Autowired
    private val userDAO: UserDAO
    ) : UserService {

    override fun create(user: User): User {
        return userDAO.save(user)
    }
}