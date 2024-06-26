package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.BaseEntity
import com.food.ordering.system.domain.vo.Money
import com.food.ordering.system.domain.vo.ProductId

class Product(
    id: ProductId,
    var name: String? = null,
    var price: Money? = null
) : BaseEntity<ProductId>(id) {

    fun updateWithConfirmedNameAndPrice(name: String, price: Money) {
        this.name = name
        this.price = price
    }
}
