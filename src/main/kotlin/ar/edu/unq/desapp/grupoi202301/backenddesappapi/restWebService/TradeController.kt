package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.UnauthorizedException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.TradeActiveDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.TradeCreateDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.TradeResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.ErrorResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TradeService
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
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
@Tag(name = "trades")
@RequestMapping("trades")
class TradeController(private val tradeService: TradeService) {
    @Autowired
    private lateinit var userService: UserService

    @Operation(summary = "Create a trade")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TradeResponseDTO::class)
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
    @PostMapping("/create")
    fun create(@RequestBody trade: TradeCreateDTO) : ResponseEntity<TradeResponseDTO> {
        val email = SecurityContextHolder.getContext().authentication.principal as String
        val user = userService.getByEmail(email)

        if(trade.idUser != user!!.id) {
            throw UnauthorizedException("token", "Cannot create a trade for another user")
        }

        val trade = tradeService.create(trade.toModel())
        val tradeResponse = TradeResponseDTO.fromModel(trade)
        return ResponseEntity.ok().body(tradeResponse)
    }

    @Operation(summary = "Get all the active trade of a user")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = TradeActiveDTO::class))
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
    @GetMapping("/active-trades/{idUser}")
    fun getTradesActive(@PathVariable("idUser") idUser: Long): ResponseEntity<List<TradeActiveDTO>> {
        val trades = tradeService.recoverActives(idUser).map { trade -> TradeActiveDTO.fromModel(trade) }
        return ResponseEntity.ok().body(trades)
    }

    @Operation(summary = "Get a trade")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TradeResponseDTO::class)
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
    @GetMapping("/{idTrade}")
    fun getTrade(@PathVariable("idTrade") idTrade: Long): ResponseEntity<TradeResponseDTO> {
        val trade = tradeService.getTrade(idTrade)
        val tradeResponse = TradeResponseDTO.fromModel(trade)
        return ResponseEntity.ok().body(tradeResponse)
    }

    @Operation(summary = "Get all trades")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = TradeResponseDTO::class))
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
    @GetMapping
    fun getTrades(): ResponseEntity<List<TradeResponseDTO>> {
        val trades = tradeService.recoverAll().map { trade -> TradeResponseDTO.fromModel(trade) }
        return ResponseEntity.ok().body(trades)
    }
}