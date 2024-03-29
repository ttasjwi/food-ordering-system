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
    val orderItems: List<OrderItem>,
    val price: Money,
    val restaurantId: RestaurantId,
    val deliveryAddress: StreetAddress,

    var trackingId: TrackingId,
    var orderStatus: OrderStatus,
    val failureMessages: MutableList<String> = mutableListOf(),
) : AggregateRoot<OrderId>(id) {

    companion object {

        /**
         * 주문 생성
         */
        fun create(
            customerId: CustomerId,
            orderItems: List<OrderItem>,
            price: Money,
            restaurantId: RestaurantId,
            deliveryAddress: StreetAddress,
        ): Order {

            val orderId = OrderId(UUID.randomUUID())
            initOrderItems(orderItems, orderId)

            return Order(
                id = orderId,
                customerId = customerId,
                orderItems = orderItems,
                price = price,
                restaurantId = restaurantId,
                deliveryAddress = deliveryAddress,
                trackingId = TrackingId(UUID.randomUUID()),
                orderStatus = OrderStatus.PENDING,
            )
        }

        private fun initOrderItems(orderItems: List<OrderItem>, orderId: OrderId) {
            var startId = 1L
            for (orderItem in orderItems) {
                orderItem.initialize(OrderItemId(startId), orderId)
                startId++
            }
        }
    }

    /**
     * 검증
     */
    fun validate() {
        validateTotalPrice()
        validateItemsPrice()
    }

    private fun validateTotalPrice() {
        if (!price.isGreaterThanZero()) {
            throw OrderDomainException("Total order price must be grater than zero!")
        }
    }

    private fun validateItemsPrice() {
        val orderItemsTotal = orderItems
            .map {
                validateItemPrice(it)
                it.subTotal
            }
            .reduce { left, right -> left.add(right) }

        if (price != orderItemsTotal) {
            throw OrderDomainException("Total Price(${price.amount}) is not equal to Order Items total(${orderItemsTotal})!")
        }
    }

    private fun validateItemPrice(orderItem: OrderItem) {
        if (!orderItem.isPriceValid()) {
            throw OrderDomainException("Order Item Price(${orderItem.price.amount}) is not valid for product ${orderItem.product.id!!.value}")
        }
    }

    /**
     * 결제
     */
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

    fun initCancel(failureMessages: List<String>) {
        if (orderStatus != OrderStatus.PAID) {
            throw OrderDomainException("Order is not in correct state for initCancel operation")

        }
        orderStatus = OrderStatus.CANCELLING
        updateFailureMessages(failureMessages)
    }

    fun cancel(failureMessages: List<String>) {
        if (!(orderStatus == OrderStatus.PENDING && orderStatus == OrderStatus.CANCELLING)) {
            throw OrderDomainException("Order is not in correct state for cancel operation")
        }
        orderStatus = OrderStatus.CANCELED
        updateFailureMessages(failureMessages)
    }

    private fun updateFailureMessages(failureMessages: List<String>) {
        this.failureMessages.addAll(failureMessages.filter { it.isNotEmpty() })
    }

}
