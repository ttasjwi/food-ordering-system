package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
internal class OrderApplicationServiceImpl(

    private val orderCreateCommandHandler: OrderCreateCommandHandler,

    private val orderTrackCommandHandler: OrderTrackCommandHandler

) : OrderApplicationService {

    override fun createOrder(command: CreateOrderCommand): CreateOrderResponse {
        return orderCreateCommandHandler.createOrder(command)
    }

    override fun trackOrder(query: TrackOrderQuery): TrackOrderResponse {
        return orderTrackCommandHandler.trackOrder(query)
    }
}
