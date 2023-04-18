package ar.edu.unq.desapp.grupoi202301.backenddesappapi.RESTwebservice

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.RESTwebservice.DTO.UserCreateDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.RESTwebservice.exception.ErrorResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@Tag(name = "users")
@RequestMapping("users")
class UserController(private val userService: UserService) {

    @Operation(summary = "Register a user")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = User::class)
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
    @PostMapping("/register")
    fun create(@RequestBody user: UserCreateDTO) : ResponseEntity<User> {
        val user = userService.create(user.toModel())
        return ResponseEntity.ok().body(user)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        ApiResponse(responseCode = "404", description = "Customer not found"),
        ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =  [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDTO::class)
                )
            ]
        )
        ]
    )
    @GetMapping
    fun obtener() = "hola"
}

