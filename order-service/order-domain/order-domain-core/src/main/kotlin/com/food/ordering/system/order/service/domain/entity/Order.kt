package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.AggregateRoot
import com.food.ordering.system.domain.vo.*
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.vo.OrderItemId
import com.food.ordering.system.order.service.domain.vo.StreetAddress
import com.food.ordering.system.order.service.domain.vo.TrackingId
import java.util.*

class Order

private constructor(
    id: OrderId,
    val customerId: CustomerId,
    val restaurantId: RestaurantId,
    val deliveryAddress: StreetAddress,

    val orderItems: List<OrderItem>,

    var trackingId: TrackingId?,
    var orderStatus: OrderStatus,
    var failureMessages: List<String> = emptyList()

) : AggregateRoot<OrderId>(id) {

    val price: Money

    init {
        this.price = calculatePrice()
    }

    private fun calculatePrice(): Money {
        return orderItems.map {
            it.subTotal
        }
            .reduce { left, right -> left.add(right) }
    }

    companion object {

        fun create(
            orderItems:List<OrderItem>,
            customerId: CustomerId,
            restaurantId: RestaurantId,
            deliveryAddress: StreetAddress,
            ): Order {

            val orderId =  OrderId(UUID.randomUUID())
            initOrderItems(orderItems, orderId)

            return Order(
                id = orderId,
                trackingId = TrackingId(UUID.randomUUID()),
                orderStatus = OrderStatus.PENDING,
                orderItems = orderItems,
                customerId = customerId,
                restaurantId = restaurantId,
                deliveryAddress = deliveryAddress,
            )
        }

        private fun initOrderItems(orderItems: List<OrderItem>, orderId: OrderId) {
            var startId = 1L
            for (orderItem in orderItems) {
                orderItem.init(OrderItemId(startId), orderId)
                startId ++
            }
        }
    }

}
