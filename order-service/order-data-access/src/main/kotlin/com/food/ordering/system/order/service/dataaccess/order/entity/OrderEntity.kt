package com.food.ordering.system.order.service.dataaccess.order.entity

import com.food.ordering.system.domain.vo.OrderStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Table(name = "orders")
@Entity
class OrderEntity (

    @Id
    @Column(name = "order_id")
    var id: UUID,

    @Column(name = "customer_id")
    var customerId: UUID,

    @Column(name = "restaurant_id")
    var restaurnt_id: UUID,

    @Column(name = "tracking_id")
    var trackingId: UUID,

    @Column(name = "price")
    var price: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    var orderStatus: OrderStatus,

    @Column(name = "failure_messages")
    var failureMessages: String,

    @OneToOne(mappedBy = "order", cascade = [CascadeType.ALL])
    var address: OrderAddressEntity,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var items: List<OrderItemEntity>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
