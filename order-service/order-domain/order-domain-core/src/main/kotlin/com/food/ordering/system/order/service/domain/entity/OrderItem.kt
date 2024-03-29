package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.BaseEntity
import com.food.ordering.system.domain.vo.Money
import com.food.ordering.system.domain.vo.OrderId
import com.food.ordering.system.order.service.domain.vo.OrderItemId

class OrderItem

private constructor(
    id: OrderItemId? = null,
    var orderId: OrderId? = null,
    val product: Product,
    val quantity: Int,
    val price: Money,
    val subTotal: Money
) : BaseEntity<OrderItemId>(id) {

    companion object {
        fun create(product: Product, quantity: Int, price: Money, subTotal: Money): OrderItem {
            return OrderItem(
                product = product,
                quantity = quantity,
                price = price,
                subTotal = subTotal
            )
        }
    }

    internal fun initialize(id: OrderItemId, orderId: OrderId) {
        super.id = id
        this.orderId = orderId
    }

    internal fun isPriceValid(): Boolean {
        return price.isGreaterThanZero() && price == product.price && price.multiply(quantity) == subTotal
    }

}
