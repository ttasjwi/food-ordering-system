package com.food.ordering.system.order.service.application.rest

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService
import com.food.ordering.system.support.logging.getLogger
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/orders"], produces = [MediaType.APPLICATION_JSON_VALUE])
class OrderController(

    private val orderApplicationService: OrderApplicationService

) {

    private val log = getLogger(javaClass)

    @PostMapping
    fun createOrder(@RequestBody command: CreateOrderCommand): ResponseEntity<CreateOrderResponse> {
        log.info { "Creating order for customer: ${command.customerId} at restaurant: ${command.restaurantId}" }
        val response = orderApplicationService.createOrder(command)

        log.info { "Order created with tracking id: ${response.orderTrackingId}" }

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/{trackingId}")
    fun getOrderByTrackingId(@PathVariable trackingId: UUID): ResponseEntity<TrackOrderResponse> {
        val query = TrackOrderQuery(orderTrackingId = trackingId)

        val response = orderApplicationService.trackOrder(query)

        log.info { "Returning order status with tracking id: ${response.orderTrackingId}" }
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}

