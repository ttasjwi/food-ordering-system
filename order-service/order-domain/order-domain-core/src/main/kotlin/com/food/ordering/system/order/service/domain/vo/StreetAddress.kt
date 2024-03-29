package com.food.ordering.system.order.service.domain.vo

import java.util.UUID

class StreetAddress(
    val id: UUID,
    val street: String,
    val postalCode: String,
    val city: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StreetAddress

        if (street != other.street) return false
        if (postalCode != other.postalCode) return false
        if (city != other.city) return false

        return true
    }

    override fun hashCode(): Int {
        var result = street.hashCode()
        result = 31 * result + postalCode.hashCode()
        result = 31 * result + city.hashCode()
        return result
    }
}
