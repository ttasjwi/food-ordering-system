package com.food.ordering.system.application.handler

import com.food.ordering.system.support.logging.getLogger
import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    protected val log = getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorDTO> {
        log.error(ex) { ex.message }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorDTO(
                    code = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
                    message = "Unexpected error!"
                )
            )
    }

    @ExceptionHandler(ValidationException::class)
    fun handleException(ex: ValidationException): ResponseEntity<ErrorDTO> {
        val errorMessage = if (ex is ConstraintViolationException) extractViolationsFromException(ex) else ex.message
        log.error(ex) { errorMessage }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorDTO(
                    code = HttpStatus.BAD_REQUEST.reasonPhrase,
                    message = errorMessage!!
                )
            )
    }


    private fun extractViolationsFromException(constraintViolationException: ConstraintViolationException): String {
        return constraintViolationException.constraintViolations.joinToString("--") { it.message }
    }

}
