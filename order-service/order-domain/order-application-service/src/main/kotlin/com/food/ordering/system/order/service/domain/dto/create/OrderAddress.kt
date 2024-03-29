package com.food.ordering.system.order.service.domain.dto.create

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotNull

class OrderAddress(

    @NotNull
    @Max(value = 50)
    val street: String?,

    @NotNull
    @Max(value = 10)
    val postalCode: String?,

    @NotNull
    @Max(value = 50)
    val city: String?,
)
