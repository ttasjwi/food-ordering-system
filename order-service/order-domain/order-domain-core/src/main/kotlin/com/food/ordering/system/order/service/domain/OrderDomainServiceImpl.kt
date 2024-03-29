package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent
import java.time.ZoneId
import java.time.ZonedDateTime

internal class OrderDomainServiceImpl : OrderDomainService {

    override fun validateAndInitOrder(order: Order, restaurant: Restaurant): OrderCreatedEvent {
        validateRestaurant(restaurant)
        crossCheckItemPrices(order, restaurant)
        order.validate()
        order.initialize()
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

    private fun validateRestaurant(restaurant: Restaurant) {
        TODO("Not yet implemented")
    }

    private fun crossCheckItemPrices(order: Order, restaurant: Restaurant) {
        TODO("Not yet implemented")
    }
}
