package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.support.logging.getLogger
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component


/**
 * aware 의 사전적 의미 : 알고 있는 -> ApplicationEventEventPublisher(발행자)를 알고 있는
 * ----> ApplicationEventPublisher를 알고 있음
 * ----> 외부에서 ApplicationEventPublisherAware를 구현한 클래스를 호출하여 간접적으로 ApplicationPublisher에게 명령 가능
 */
@Component
class ApplicationDomainEventPublisher
    : ApplicationEventPublisherAware, DomainEventPublisher<OrderCreatedEvent> {

    private lateinit var applicationEventPublisher: ApplicationEventPublisher
    private val log = getLogger(javaClass)

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher
    }

    override fun publish(domainEvent: OrderCreatedEvent) {
        this.applicationEventPublisher.publishEvent(domainEvent)
        log.info { "OrderCreatedEvent is published for order id: ${domainEvent.order.id!!.value}" }
    }
}
