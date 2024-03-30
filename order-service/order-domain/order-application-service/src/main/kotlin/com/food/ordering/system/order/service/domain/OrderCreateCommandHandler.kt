package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import com.food.ordering.system.support.logging.getLogger
import org.springframework.stereotype.Component

@Component
class OrderCreateCommandHandler(

    private val orderCreateHelper: OrderCreateHelper,
    private val orderDataMapper: OrderDataMapper,
    private val orderCreatedPaymentRequestMessagePublisher: OrderCreatedPaymentRequestMessagePublisher
) {

    private val log = getLogger(javaClass)

    fun createOrder(command: CreateOrderCommand): CreateOrderResponse {
        val orderCreatedEvent = orderCreateHelper.persistOrder(command)
        log.info { "Order is created with id: ${orderCreatedEvent.order.id!!.value}" }

        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent)

        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.order, "Order created successfully")
    }
}
