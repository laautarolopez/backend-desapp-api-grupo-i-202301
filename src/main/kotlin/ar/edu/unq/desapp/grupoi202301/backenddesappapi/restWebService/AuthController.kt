package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.LoginDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.ErrorResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.security.jwt.JwtUtil
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@Tag(name = "auth")
@RequestMapping
class AuthController {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Operation(summary = "Login")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad Request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponseDTO::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody login: LoginDTO, response: HttpServletResponse) : ResponseEntity<String> {
        val user = userService.getByEmail(login.email!!)

        if (jwtUtil.isPasswordOnExistingUser(user, login)) {
            val token = jwtUtil.generateToken(user!!.email!!)
            response.addHeader("Authorization", token)
            return ResponseEntity.ok().body("Login successful")
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password wrong")
        }
    }
}