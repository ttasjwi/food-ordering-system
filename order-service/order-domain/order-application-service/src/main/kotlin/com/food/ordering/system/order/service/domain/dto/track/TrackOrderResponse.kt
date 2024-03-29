package com.food.ordering.system.order.service.domain.dto.track

import com.food.ordering.system.domain.vo.OrderStatus
import java.util.UUID

class TrackOrderResponse (

    val orderTrackingId: UUID,
    val orderStatus: OrderStatus,
    val failureMessages: List<String>,
)