package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.AggregateRoot
import com.food.ordering.system.domain.vo.*
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.vo.OrderItemId
import com.food.ordering.system.order.service.domain.vo.StreetAddress
import com.food.ordering.system.order.service.domain.vo.TrackingId
import java.util.*
import kotlin.collections.ArrayList

class Order
private constructor(
    id: OrderId,
    val customerId: CustomerId,
    val restaurantId: RestaurantId,
    val deliveryAddress: StreetAddress,

    val orderItems: List<OrderItem>,

    var trackingId: TrackingId?,
    var orderStatus: OrderStatus,
    val failureMessages: MutableList<String> = ArrayList()

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

    fun pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw OrderDomainException("Order is not in correct state for pay operation")
        }
        orderStatus = OrderStatus.PAID
    }

    fun approve() {
        if (orderStatus != OrderStatus.PAID) {
            throw OrderDomainException("Order is not in correct state for approve operation")
        }
        orderStatus = OrderStatus.APPROVED
    }

    fun initCancle(failureMessages: List<String>) {
        if (orderStatus != OrderStatus.PAID) {
            throw OrderDomainException("Order is not in correct state for initCancel operation")

        }
        orderStatus = OrderStatus.CANCELLING
        updateFailureMessages(failureMessages)
    }

    fun cancle(failureMessages: List<String>) {
        if (!(orderStatus == OrderStatus.PENDING && orderStatus == OrderStatus.CANCELLING)) {
            throw OrderDomainException("Order is not in correct state for cancel operation")
        }
        orderStatus = OrderStatus.CANCELED
        updateFailureMessages(failureMessages)
    }

    private fun updateFailureMessages(failureMessages: List<String>) {
        this.failureMessages.addAll(failureMessages.filter { it.isNotEmpty() })
    }

    companion object {

        fun create(
            orderItems: List<OrderItem>,
            customerId: CustomerId,
            restaurantId: RestaurantId,
            deliveryAddress: StreetAddress,
        ): Order {

            val orderId = OrderId(UUID.randomUUID())
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
                startId++
            }
        }
    }

}
