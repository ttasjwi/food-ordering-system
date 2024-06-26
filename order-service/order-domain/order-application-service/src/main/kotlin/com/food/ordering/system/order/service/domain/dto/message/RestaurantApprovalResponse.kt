package com.food.ordering.system.order.service.domain.dto.message

import com.food.ordering.system.domain.vo.OrderApprovalStatus
import java.time.Instant

class RestaurantApprovalResponse(

    val id: String,
    val sagaId: String,
    val orderId: String,
    val restaurantId: String,
    val createdAt: Instant,
    val orderApprovalStatus: OrderApprovalStatus,
    val failureMessages: List<String>
)
