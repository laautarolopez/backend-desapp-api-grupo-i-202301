package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.aspect.LogExecutionTime
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.ErrorResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.QuoteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@Tag(name = "quote")
@RequestMapping("quotes")
class QuoteController(private val quoteService: QuoteService) {

    @LogExecutionTime
    @Operation(summary = "Get the list of quotes (updated every 10 minutes)")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = Quote::class))
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
    @GetMapping("/quoteList")
    fun getQuotesList(): ResponseEntity<List<Quote>> {
        return ResponseEntity.ok().body(quoteService.getQuotesList())
    }
}