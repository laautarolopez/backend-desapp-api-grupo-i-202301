package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.UnauthorizedException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.VolumeOperatedResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.ErrorResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.VolumeOperatedService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@Tag(name = "volume")
@RequestMapping("volume")
class VolumeOperatedController(private val volumeOperatedService: VolumeOperatedService) {

    @Autowired
    private lateinit var userService: UserService

    @Operation(summary = "Get the volume operated of a user between two active crypto dates.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = VolumeOperatedResponseDTO::class)
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
    @GetMapping("/{idUser}/{firstDate}/{lastDate}")
    fun getVolumeOperatedCryptos(@PathVariable("idUser") idUser: Long, @PathVariable("firstDate") firstDate: String, @PathVariable("lastDate") lastDate: String): ResponseEntity<VolumeOperatedResponseDTO> {
        val email = SecurityContextHolder.getContext().authentication.principal as String
        val user = userService.getByEmail(email)

        if(idUser != user!!.id) {
            throw UnauthorizedException("token", "Cannot see a volume operated for another user")
        }

        var volume = volumeOperatedService.volumeOperatedByAUserBetweenDates(idUser, firstDate, lastDate)
        var response = VolumeOperatedResponseDTO.fromModel(volume)
        return ResponseEntity.ok().body(response)
    }
}