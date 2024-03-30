package com.food.ordering.system.order.service.domain.dto.create

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

    @NotNull
    val orderAddress: OrderAddress?

)
