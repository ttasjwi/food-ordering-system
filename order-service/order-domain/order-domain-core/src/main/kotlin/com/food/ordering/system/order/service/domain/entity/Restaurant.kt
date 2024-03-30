package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.AggregateRoot
import com.food.ordering.system.domain.vo.RestaurantId

class Restaurant(
    id: RestaurantId,
    val products: List<Product> = listOf(),
    var isActive: Boolean = false
) : AggregateRoot<RestaurantId>(id)