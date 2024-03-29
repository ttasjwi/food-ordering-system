package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.AggregateRoot
import com.food.ordering.system.domain.vo.CustomerId

class Customer(
    id: CustomerId
) : AggregateRoot<CustomerId>(id)