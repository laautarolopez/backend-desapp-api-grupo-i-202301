package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.aspect.LogExecutionTime
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.Quote24hs
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.CryptoCreateDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.CryptoSimpleDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.externalApi.PriceResponse
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception.ErrorResponseDTO
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.CryptoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
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
@Tag(name = "cryptos")
@RequestMapping("cryptos")
class CryptoController(private val cryptoService: CryptoService) {

    @Operation(summary = "Create a crypto")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CryptoSimpleDTO::class)
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
    fun create(@RequestBody crypto: CryptoCreateDTO) : ResponseEntity<CryptoSimpleDTO> {
        val crypto = cryptoService.create(crypto.toModel())
        return ResponseEntity.ok().body(CryptoSimpleDTO(crypto.id, crypto.name))
    }

    @Operation(summary = "Get price of a crypto")
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = PriceResponse::class)
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
    @GetMapping("/prices/{cryptoName}")
    fun getPrice(@PathVariable("cryptoName") cryptoName: String): ResponseEntity<PriceResponse> {
        val price = cryptoService.getPrice(cryptoName)
        return ResponseEntity.ok().body(price)
    }

    @Operation(summary = "Get prices of the last 24 hours of a crypto")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = Quote24hs::class))
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
    @GetMapping("/prices24hs/{cryptoName}")
    fun getPrices24hs(@PathVariable("cryptoName") cryptoName: String): ResponseEntity<List<Quote24hs>> {
        val quotes24hs = cryptoService.getQuotes24hs(cryptoName)
        return ResponseEntity.ok().body(quotes24hs)
    }

    @LogExecutionTime
    @Operation(summary = "Get prices of cryptos")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = PriceResponse::class))
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
    @GetMapping("/prices")
    fun getPrices(): ResponseEntity<List<PriceResponse>> {
        val prices = cryptoService.getPrices()
        return ResponseEntity.ok().body(prices)
    }
}