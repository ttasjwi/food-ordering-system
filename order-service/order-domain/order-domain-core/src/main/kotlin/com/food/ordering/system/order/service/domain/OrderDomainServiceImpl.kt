package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.support.logging.getLogger
import java.time.ZoneId
import java.time.ZonedDateTime

internal class OrderDomainServiceImpl : OrderDomainService {

    private val log = getLogger(javaClass)

    override fun validateAndInitOrder(order: Order, restaurant: Restaurant): OrderCreatedEvent {
        checkRestaurantIsActivated(restaurant)
        order.updateProductsWithCurrentInformation(restaurant)
        order.validate()
        order.initialize()

        log.info { "Order with id: ${order.id!!.value} is initiated" }
        return OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")))
    }

    override fun payOrder(order: Order): OrderPaidEvent {
        TODO("Not yet implemented")
    }

    override fun approveOrder(order: Order) {
        TODO("Not yet implemented")
    }

    override fun cancelOrderPayment(order: Order, failureMessages: List<String>): OrderCancelledEvent {
        TODO("Not yet implemented")
    }

    override fun cancelOrder(order: Order, failureMessages: List<String>) {
        TODO("Not yet implemented")
    }

    private fun checkRestaurantIsActivated(restaurant: Restaurant) {
        if (restaurant.isActive) {
            throw OrderDomainException("Restaurant with id ${restaurant.id!!.value} is currently not active!")
        }
    }
}
