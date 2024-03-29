package com.food.ordering.system.order.service.domain.dto.create

import com.food.ordering.system.order.service.domain.entity.OrderItem
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.util.UUID

class CreateOrderCommand (

    @NotNull
    val customerId: UUID?,

    @NotNull
    val restaurantId: UUID?,

    @NotNull
    val price: BigDecimal?,

    @NotNull
    val orderItems: List<OrderItem>?,

)
