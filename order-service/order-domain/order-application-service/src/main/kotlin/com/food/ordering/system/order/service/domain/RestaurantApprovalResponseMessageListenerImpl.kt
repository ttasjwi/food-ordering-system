package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse
import com.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessasgeListener
import com.food.ordering.system.support.logging.getLogger
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated


@Validated
@Service
class RestaurantApprovalResponseMessageListenerImpl : RestaurantApprovalResponseMessasgeListener {

    private val log = getLogger(javaClass)

    override fun orderApproved(restaurantApprovalResponse: RestaurantApprovalResponse) {
        TODO("Not yet implemented")
    }

    override fun orderRejected(restaurantApprovalResponse: RestaurantApprovalResponse) {
        TODO("Not yet implemented")
    }

}
