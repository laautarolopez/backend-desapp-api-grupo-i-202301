package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<*> {
        val errors = ex.bindingResult
            .fieldErrors
            .map { obj: FieldError -> ErrorDTO(obj.field, obj.defaultMessage ?: "") }
        val body = ErrorResponseDTO(errors)
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<*> {
        val errors = ex.constraintViolations
            .map(ErrorDTO::of)
        val body = ErrorResponseDTO(errors)
        return ResponseEntity.badRequest().body(body)
    }
}