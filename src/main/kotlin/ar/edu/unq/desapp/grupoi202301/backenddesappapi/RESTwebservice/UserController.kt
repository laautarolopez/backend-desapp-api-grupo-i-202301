package ar.edu.unq.desapp.grupoi202301.backenddesappapi.RESTwebservice

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.*
@RestController
@Transactional
@RequestMapping("/api/crypto")
class UserController(private val userService: UserService) {

}

