package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.BaseEntity
import com.food.ordering.system.domain.vo.Money
import com.food.ordering.system.domain.vo.OrderId
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.vo.OrderItemId

class OrderItem

private constructor (

    id: OrderItemId? = null,
    var orderId: OrderId? = null,
    val product: Product,
    val quantity: Int,
) : BaseEntity<OrderItemId>(id) {

    val price: Money
    val subTotal: Money

    init {
        price = product.price

        validatePrice()
        validateQuantity()
        subTotal = calculateSubTotal()
    }

    private fun validateQuantity() {
        if (quantity <= 0) {
            throw OrderDomainException("OrderItem quantity must be greater than zero")
        }
    }

    private fun calculateSubTotal(): Money {
        return price.multiply(quantity)
    }

    private fun validatePrice() {
        if (!price.isGreaterThanZero()) {
            throw OrderDomainException("OrderItem price must be greater than zero")
        }
    }

    companion object {
        fun create(product: Product, quantity: Int): OrderItem {
            return OrderItem(
                product = product,
                quantity = quantity
            )
        }
    }

    internal fun init(id: OrderItemId, orderId: OrderId) {
        initId(id)
        this.orderId = orderId
    }

}
