package com.food.ordering.system.order.service.domain.ports.output.repository

import com.food.ordering.system.domain.vo.CustomerId
import com.food.ordering.system.order.service.domain.entity.Customer

interface CustomerRepository {

    fun save(customer: Customer): Customer
    fun findCustomer(customerId: CustomerId): Customer?
}
