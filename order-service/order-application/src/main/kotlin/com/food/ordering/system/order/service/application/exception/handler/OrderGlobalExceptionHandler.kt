package com.food.ordering.system.order.service.application.exception.handler

import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException
import com.food.ordering.system.support.logging.getLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class OrderGlobalExceptionHandler {

    private val log = getLogger(javaClass)

    @ExceptionHandler(OrderDomainException::class)
    fun handleException(ex: OrderDomainException): ResponseEntity<ErrorDTO> {
        log.error(ex) { ex.message }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorDTO(
                    code= HttpStatus.BAD_REQUEST.reasonPhrase,
                    message= ex.message!!
                )
            )
    }

    @ExceptionHandler(OrderNotFoundException::class)
    fun handleException(ex: OrderNotFoundException): ResponseEntity<ErrorDTO> {
        log.error(ex) { ex.message }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                ErrorDTO(
                    code= HttpStatus.NOT_FOUND.reasonPhrase,
                    message= ex.message!!
                )
            )
    }
}
