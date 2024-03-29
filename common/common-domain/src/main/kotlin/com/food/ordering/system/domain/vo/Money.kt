package com.food.ordering.system.domain.vo

import java.math.BigDecimal

data class Money (
    val amount: BigDecimal
) {
    companion object {
        val ZERO = Money(BigDecimal.ZERO)
    }

    fun isGreaterThanZero(): Boolean {
        return this.amount > ZERO.amount
    }

    fun isGreaterThan(money: Money): Boolean {
        return this.amount > money.amount
    }

    fun add(money: Money): Money {
        return Money(amount + money.amount)
    }

    fun subtract(money: Money): Money {
        return Money(amount - money.amount)
    }

    fun multiply(multiplier: Int): Money {
        return Money (amount * BigDecimal(multiplier))
    }
}
