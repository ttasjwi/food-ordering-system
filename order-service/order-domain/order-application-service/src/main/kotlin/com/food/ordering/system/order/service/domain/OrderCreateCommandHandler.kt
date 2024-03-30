package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository
import com.food.ordering.system.support.logging.getLogger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class OrderCreateCommandHandler(

    private val orderDomainService: OrderDomainService,

    private val orderRepository: OrderRepository,

    private val customerRepository: CustomerRepository,

    private val restaurantRepository: RestaurantRepository,

    private val orderDataMapper: OrderDataMapper
) {

    private val log = getLogger(javaClass)

    @Transactional
    fun createOrder(command: CreateOrderCommand): CreateOrderResponse {
        checkCustomerExistence(command.customerId!!)
        val restaurant = checkRestaurant(command)
        val order = orderDataMapper.createOrderCommandToOrder(command)

        val orderCreatedEvent = orderDomainService.validateAndInitOrder(order, restaurant)
        val savedOrder = saveOrder(orderCreatedEvent.order)
        log.info { "Order is created with id: ${savedOrder.id!!.value}" }
        val createOrderResponse = orderDataMapper.orderToCreateOrderResponse(savedOrder,  "Order created successfully")

        return createOrderResponse
    }

    private fun saveOrder(order: Order): Order {
        val savedOrder = orderRepository.save(order)
        if (savedOrder === null) {
            log.error { "Could not save Order!" }
            throw OrderDomainException("Could not save Order!")
        }
        log.info { "Order is saved with id: ${savedOrder.id!!.value}" }
        return savedOrder
    }

    private fun checkCustomerExistence(customerId: UUID) {
        customerRepository.findCustomer(customerId)
            ?: {
                log.warn { "Could not find customer with customer id: $customerId" }
                throw OrderDomainException("Could not find customer with customer id: $customerId")
            }
    }

    private fun checkRestaurant(command: CreateOrderCommand): Restaurant {
        val restaurant = orderDataMapper.createOrderCommandToRestaurant(command)
        val findRestaurant = restaurantRepository.findRestaurantInformation(restaurant)

        if (findRestaurant == null) {
            log.warn { "Could not find restaurant id: ${command.restaurantId}" }
            throw OrderDomainException("Could not find restaurant id: ${command.restaurantId}")

        }
        return findRestaurant
    }


}
