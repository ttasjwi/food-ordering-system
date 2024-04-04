package com.food.ordering.system.order.service.dataaccess.order.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Table(name = "order_item")
@IdClass(OrderItemEntityId::class)
@Entity
class OrderItemEntity (

    @Id
    @Column(name = "order_item_id")
    var id: Long,

    @Id
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "order_id")
    var order: OrderEntity,

    @Column(name = "product_id")
    var productId: UUID,

    @Column(name = "price")
    var price: BigDecimal,

    @Column(name = "quantity")
    var quantity: Int,

    @Column(name = "sub_total")
    var subTotal: BigDecimal


) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderItemEntity

        if (id != other.id) return false
        if (order != other.order) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + order.hashCode()
        return result
    }
}
