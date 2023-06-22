package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.ActionEmptyException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.OperationEmptyException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.UnauthorizedException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions.ViolationException
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.exception.CryptoNonExistent
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.exception.UserNonExistent
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

    @ExceptionHandler(BinanceResponseException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBinanceResponseException(ex: BinanceResponseException): ResponseEntity<*> {
        val body = ErrorBinanceResponse(ex.cryptoName, ex.newMessage)
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(BinanceServerException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleBinanceServerException(ex: BinanceServerException): ResponseEntity<*> {
        val body = ErrorBinanceServer(ex.newMessage)
        return ResponseEntity.internalServerError().body(body)
    }

    @ExceptionHandler(CryptoNonExistent::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleCryptoNonExistentException(ex: CryptoNonExistent): ResponseEntity<*> {
        val body = ErrorDTO("Crypto", ex.message!!)
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(UserNonExistent::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUserNonExistentException(ex: UserNonExistent): ResponseEntity<*> {
        val body = ErrorDTO("User", ex.message!!)
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(OperationEmptyException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleParserException(ex: OperationEmptyException): ResponseEntity<*> {
        val body = ErrorDTO("Operation", ex.message!!)
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(ActionEmptyException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleParserException(ex: ActionEmptyException): ResponseEntity<*> {
        val body = ErrorDTO("Action", ex.message!!)
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(ViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleParserException(ex: ViolationException): ResponseEntity<*> {
        val body = ErrorDTO(ex.attribute, ex.message!!)
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleParserException(ex: UnauthorizedException): ResponseEntity<*> {
        val body = ErrorDTO(ex.attribute, ex.message!!)
        return ResponseEntity.badRequest().body(body)
    }
}