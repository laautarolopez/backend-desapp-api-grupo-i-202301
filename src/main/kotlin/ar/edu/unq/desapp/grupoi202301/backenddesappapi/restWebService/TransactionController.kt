package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.TransactionCreateDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.TransactionDTO
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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
                        schema = Schema(implementation = TransactionDTO::class)
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
    fun create(@RequestBody transaction: TransactionCreateDTO) : ResponseEntity<Transaction> {
        val transaction = transactionService.create(transaction.toModel())
        return ResponseEntity.ok().body(transaction)
    }
}