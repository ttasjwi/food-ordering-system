package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener
import com.food.ordering.system.support.logging.getLogger
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

/**
 * 결제 이벤트를 처리
 */
@Validated
@Service
class PaymentResponseMessageListenerImpl : PaymentResponseMessageListener{

    private val log = getLogger(javaClass)

    /**
     * 결제 완료 -> PAID 상태로 변경해야함
     */
    override fun paymentCompleted(paymentResponse: PaymentResponse) {
        TODO("Not yet implemented")
    }

    /**
     * 결제 취소됨 ->
     */
    override fun paymentCancelled(paymentCancelled: PaymentResponse) {
        TODO("Not yet implemented")
    }

}
