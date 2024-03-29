package com.food.ordering.system.order.service.domain.ports.input.message.listener.payment

import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse

interface PaymentResponseMessageListener {

    fun paymentCompleted(paymentResponse: PaymentResponse)
    fun paymentCancelled(paymentCancelled: PaymentResponse)
}