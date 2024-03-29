package com.food.ordering.system.order.service.domain.dto.create

import com.food.ordering.system.domain.vo.OrderStatus
import java.util.UUID

class CreateOrderResponse (
    val orderTrackingId: UUID,
    val orderStatus: OrderStatus,
    val message: String,
)
