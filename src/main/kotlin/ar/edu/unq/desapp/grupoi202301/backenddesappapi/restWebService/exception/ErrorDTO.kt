package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception

import jakarta.validation.ConstraintViolation

data class ErrorDTO(
    val attribute: String,
    val message: String
) {
    companion object {
        fun of(constraintViolation: ConstraintViolation<*>): ErrorDTO {
            val propertyPath = constraintViolation.propertyPath.toString().substringAfter(".")
            return ErrorDTO(
                propertyPath,
                constraintViolation.message
            )
        }
    }

    constructor() : this("", "")
}

data class ErrorResponseDTO(val errors: List<ErrorDTO>)

data class ErrorBinanceResponse(val cryptoName: String, val msg: String)

data class ErrorBinanceServer(val msg: String)