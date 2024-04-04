package com.food.ordering.system.order.service.dataaccess.order.entity

import jakarta.persistence.Embeddable
import java.io.Serializable

data class OrderItemEntityId(
    val id: Long,
    val orderEntity: OrderEntity
) : Serializable
