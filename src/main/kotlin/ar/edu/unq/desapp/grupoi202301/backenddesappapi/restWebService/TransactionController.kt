package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.*
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.ErrorResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.TransactionService
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
@Tag(name = "transactions")
@RequestMapping("transactions")
class TransactionController(private val transactionService: TransactionService) {

    @Operation(summary = "Create a transaction")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TransactionResponseDTO::class)
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
    fun create(@RequestBody transaction: TransactionCreateDTO) : ResponseEntity<TransactionResponseDTO> {
        val transaction = transactionService.create(transaction.toModel())
        val transactionResponse = TransactionResponseDTO.fromModel(transaction)
        return ResponseEntity.ok().body(transactionResponse)
    }

    @Operation(summary = "Transfer in a transaction")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TransactionResponseDTO::class)
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
    @PutMapping("/transfer")
    fun transfer(@RequestBody transaction: TransactionRequestDTO) : ResponseEntity<TransactionResponseDTO> {
        val transaction = transactionService.transfer(transaction.toModel())
        val transactionResponse = TransactionResponseDTO.fromModel(transaction)
        return ResponseEntity.ok().body(transactionResponse)
    }

    @Operation(summary = "Confirm a transaction")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TransactionResponseDTO::class)
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
    @PutMapping("/confirm")
    fun confirm(@RequestBody transaction: TransactionRequestDTO) : ResponseEntity<TransactionResponseDTO> {
        val transaction = transactionService.confirm(transaction.toModel())
        val transactionResponse = TransactionResponseDTO.fromModel(transaction)
        return ResponseEntity.ok().body(transactionResponse)
    }

    @Operation(summary = "Cancel a transaction")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TransactionResponseDTO::class)
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
    @PutMapping("/cancel")
    fun cancel(@RequestBody transaction: TransactionRequestDTO) : ResponseEntity<TransactionResponseDTO> {
        val transaction = transactionService.cancel(transaction.toModel())
        val transactionResponse = TransactionResponseDTO.fromModel(transaction)
        return ResponseEntity.ok().body(transactionResponse)
    }

    // TODO: agregar metodo GET
}