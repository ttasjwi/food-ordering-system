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

class OrderDomainServiceImpl : OrderDomainService {

    private val log = getLogger(javaClass)

    companion object {
        private const val UTC = "UTC"
    }

    override fun validateAndInitOrder(order: Order, restaurant: Restaurant): OrderCreatedEvent {
        checkRestaurantIsActivated(restaurant)
        order.updateProductsWithCurrentInformation(restaurant)
        order.validate()
        order.initialize()

        log.info { "Order with id: ${order.id!!.value} is initiated" }
        return OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)))
    }

    override fun payOrder(order: Order): OrderPaidEvent {
        order.pay()
        log.info { "Order with id: ${order.id!!.value} is paid" }
        return OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)))
    }

    override fun approveOrder(order: Order) {
        order.approve()
        log.info { "Order with id: ${order.id!!.value} is approved" }
    }

    override fun cancelOrderPayment(order: Order, failureMessages: List<String>): OrderCancelledEvent {
        order.initCancel(failureMessages)
        log.info { "Order payment is cancelling for order id: ${order.id!!.value}" }

        return OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)))
    }

    override fun cancelOrder(order: Order, failureMessages: List<String>) {
        order.cancel(failureMessages)
        log.info { "Order with id: ${order.id!!.value} is cancelled" }
    }

    private fun checkRestaurantIsActivated(restaurant: Restaurant) {
        if (!restaurant.isActive) {
            throw OrderDomainException("Restaurant with id ${restaurant.id!!.value} is currently not active!")
        }
    }
}
