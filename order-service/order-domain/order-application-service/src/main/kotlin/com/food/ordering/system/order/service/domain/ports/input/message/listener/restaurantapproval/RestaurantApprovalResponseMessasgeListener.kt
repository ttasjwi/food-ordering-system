package com.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval

import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse

interface RestaurantApprovalResponseMessasgeListener {

    fun orderApproved(restaurantApprovalResponse: RestaurantApprovalResponse)
    fun orderRejected(restaurantApprovalResponse: RestaurantApprovalResponse)
}
