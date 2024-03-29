package com.food.ordering.system.order.service.domain.ports.output.repository

import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.vo.TrackingId

interface OrderRepository {

    fun save(order: Order): Order

    fun findByTrackingId(trackingId: TrackingId): Order?

}
