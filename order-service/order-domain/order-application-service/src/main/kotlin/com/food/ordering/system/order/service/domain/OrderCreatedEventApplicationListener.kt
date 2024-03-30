package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener


@Component
class OrderCreatedEventApplicationListener (
    private val orderCreatedPaymentRequestMessagePublisher: OrderCreatedPaymentRequestMessagePublisher
) {

    /**
     * 트랜잭션이 커밋되고 이벤트 리스닝(내부이벤트) 보장
     */
    @TransactionalEventListener
    fun process(orderCreatedEvent: OrderCreatedEvent) {

        // 외부로 나가는 이벤트 발행
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent)
    }
}
