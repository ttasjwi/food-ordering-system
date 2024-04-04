package com.food.ordering.system.order.service.dataaccess.order.entity

import jakarta.persistence.*
import java.util.UUID

@Table(name = "order_address")
@Entity
class OrderAddressEntity (

    @Id
    @Column(name = "order_address_id")
    var id: UUID,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "order_id")
    var order: OrderEntity,

    @Column(name = "street")
    var street: String,

    @Column(name = "postal_code")
    var postalCode: String,

    @Column(name = "city")
    var city: String,


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderAddressEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

