package com.food.ordering.system.order.service.domain.mapper

import com.food.ordering.system.domain.vo.CustomerId
import com.food.ordering.system.domain.vo.Money
import com.food.ordering.system.domain.vo.ProductId
import com.food.ordering.system.domain.vo.RestaurantId
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.OrderItem
import com.food.ordering.system.order.service.domain.entity.Product
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.vo.StreetAddress
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderDataMapper {

    fun createOrderCommandToRestaurant(createOrderCommand: CreateOrderCommand): Restaurant {
        return Restaurant(
            id = RestaurantId(createOrderCommand.restaurantId!!),
            products = createOrderCommand.orderItems!!.map { Product(ProductId(it.id!!)) }.toMutableList(),
        )
    }

    fun createOrderCommandToOrder(createOrderCommand: CreateOrderCommand): Order {
        return Order.create(
            customerId = CustomerId(createOrderCommand.customerId!!),
            orderItems = orderItemsToOrderItemEntity(createOrderCommand.orderItems!!),
            restaurantId = RestaurantId(createOrderCommand.restaurantId!!),
            price = Money(createOrderCommand.price!!),
            deliveryAddress = orderAddressToStreetAddress(createOrderCommand.orderAddress!!)
        )
    }

    fun orderToCreateOrderResponse(order: Order, message:String): CreateOrderResponse {
        return CreateOrderResponse(
            orderTrackingId = order.trackingId!!.value,
            orderStatus = order.orderStatus,
            message = message,
        )
    }

    fun orderToTrackOrderResponse(order: Order): TrackOrderResponse {
        return TrackOrderResponse(
            orderTrackingId = order.trackingId!!.value,
            orderStatus = order.orderStatus,
            failureMessages = order.failureMessages
        )
    }

    private fun orderAddressToStreetAddress(orderAddress: OrderAddress): StreetAddress {
        return StreetAddress(
            id = UUID.randomUUID(),
            street = orderAddress.street!!,
            postalCode = orderAddress.postalCode!!,
            city = orderAddress.city!!
        )
    }

    private fun orderItemsToOrderItemEntity(orderItems: List<com.food.ordering.system.order.service.domain.dto.create.OrderItem>)
            : List<OrderItem> {

        return orderItems.map {
            OrderItem.create(
                product = Product(ProductId(it.id!!)),
                quantity = it.quantity!!,
                price = Money(it.price!!),
                subTotal = Money(it.subTotal!!)
            )
        }
    }


}
