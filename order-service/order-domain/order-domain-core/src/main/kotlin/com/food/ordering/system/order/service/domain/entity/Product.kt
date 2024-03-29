package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.BaseEntity
import com.food.ordering.system.domain.vo.Money
import com.food.ordering.system.domain.vo.ProductId

class Product(
    id: ProductId,
    var name: String,
    var price: Money
) : BaseEntity<ProductId>(id)