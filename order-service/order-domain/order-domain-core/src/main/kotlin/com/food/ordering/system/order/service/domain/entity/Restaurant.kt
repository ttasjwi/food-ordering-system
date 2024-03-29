package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.AggregateRoot
import com.food.ordering.system.domain.vo.RestaurantId

class Restaurant(
    id: RestaurantId,
    val products: MutableList<Product> = mutableListOf(),
    var active: Boolean
) : AggregateRoot<RestaurantId>(id)
