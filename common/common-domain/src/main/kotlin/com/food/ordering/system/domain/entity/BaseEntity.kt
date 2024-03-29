package com.food.ordering.system.domain.entity

abstract class BaseEntity<ID> (
    var id: ID? = null
) {

    fun initId(id: ID) {
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseEntity<*>

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
