package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.vo.*
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress
import com.food.ordering.system.order.service.domain.dto.create.OrderItem
import com.food.ordering.system.order.service.domain.entity.Customer
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.Product
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.util.*

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 라이프 사이클 : 클래스단위
@SpringBootTest(classes = [OrderTestConfiguration::class])
class OrderApplicationServiceTest

@Autowired constructor(

    private val orderApplicationService: OrderApplicationService,
    private val orderDataMapper: OrderDataMapper,
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val restaurantRepository: RestaurantRepository,
) {

    private val CUSTOMER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41")
    private val RESTAURANT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45")
    private val PRODUCT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48")
    private val ORDER_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afb")
    private val PRICE = BigDecimal("200.00")

    private lateinit var createOrderCommand: CreateOrderCommand
    private lateinit var createOrderCommandWrongPrice: CreateOrderCommand
    private lateinit var createOrderCommandWrongProductPrice: CreateOrderCommand


    @BeforeAll
    fun init() {
        createOrderCommand = CreateOrderCommand(
            customerId = CUSTOMER_ID,
            restaurantId = RESTAURANT_ID,
            price = PRICE,
            orderItems = listOf(
                OrderItem(
                    id= PRODUCT_ID,
                    quantity = 1,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("50.00")
                ),
                OrderItem(
                    id= PRODUCT_ID,
                    quantity = 3,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("150.00")
                )
            ),
            orderAddress = OrderAddress(
                street = "street_1",
                postalCode = "1000AB",
                city = "Paris"
            )
        )

        createOrderCommandWrongPrice = CreateOrderCommand(
            customerId = CUSTOMER_ID,
            restaurantId = RESTAURANT_ID,
            price = BigDecimal("250.00"),
            orderItems = listOf(
                OrderItem(
                    id= PRODUCT_ID,
                    quantity = 1,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("50.00")
                ),
                OrderItem(
                    id= PRODUCT_ID,
                    quantity = 3,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("150.00")
                )
            ),
            orderAddress = OrderAddress(
                street = "street_1",
                postalCode = "1000AB",
                city = "Paris"
            )
        )

        createOrderCommandWrongProductPrice = CreateOrderCommand(
            customerId = CUSTOMER_ID,
            restaurantId = RESTAURANT_ID,
            price = BigDecimal("210.00"),
            orderItems = listOf(
                OrderItem(
                    id= PRODUCT_ID,
                    quantity = 1,
                    price = BigDecimal("60.00"),
                    subTotal = BigDecimal("60.00")
                ),
                OrderItem(
                    id= PRODUCT_ID,
                    quantity = 3,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("150.00")
                )
            ),
            orderAddress = OrderAddress(
                street = "street_1",
                postalCode = "1000AB",
                city = "Paris"
            )
        )

        val customer = Customer(CustomerId(CUSTOMER_ID))

        val activeRestaurant = Restaurant(
            id = RestaurantId(RESTAURANT_ID),
            products = listOf(
                Product(
                    id = ProductId(PRODUCT_ID),
                    name = "product-1",
                    price = Money(BigDecimal("50.00"))
                ),
                Product(
                    id = ProductId(PRODUCT_ID),
                    name = "product-2",
                    price = Money(BigDecimal("50.00"))
                )
            ),
            isActive = true
        )

        val order = orderDataMapper.createOrderCommandToOrder(createOrderCommand)
        order.initId(OrderId(ORDER_ID))

        Mockito.`when`(customerRepository.findCustomer(CUSTOMER_ID))
            .thenReturn(customer)

        Mockito.`when`(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
            .thenReturn(activeRestaurant)

        Mockito.`when`(orderRepository.save(any<Order>())).thenReturn(order)
    }

    @Test
    fun testCreateOrder() {
        // when
        val createdOrderResponse = orderApplicationService.createOrder(createOrderCommand)

        // then
        assertThat(createdOrderResponse.orderStatus).isEqualTo(OrderStatus.PENDING)
        assertThat(createdOrderResponse.message).isEqualTo("Order created successfully")
        assertThat(createdOrderResponse.orderTrackingId).isNotNull()
    }

    @Test
    fun testCreateOrderWithWrongTotalPrice() {
        // when
        assertThatThrownBy { orderApplicationService.createOrder(createOrderCommandWrongPrice) }
            .isInstanceOf(OrderDomainException::class.java)
            .hasMessage("Total price(250.00) is not equal to Order Items total(200.00)!")
    }

    @Test
    fun testCreateOrderWithWrongProductPrice() {
        // when
        assertThatThrownBy { orderApplicationService.createOrder(createOrderCommandWrongProductPrice) }
            .isInstanceOf(OrderDomainException::class.java)
            .hasMessage("Order Item Price(60.00) is not valid for product d215b5f8-0249-4dc5-89a3-51fd148cfb48")
    }

    @Test
    fun testCreateOrderWithPassiveRestaurant() {
        val passiveRestaurant = Restaurant(
            id = RestaurantId(RESTAURANT_ID),
            products = listOf(
                Product(
                    id = ProductId(PRODUCT_ID),
                    name = "product-1",
                    price = Money(BigDecimal("50.00"))
                ),
                Product(
                    id = ProductId(PRODUCT_ID),
                    name = "product-2",
                    price = Money(BigDecimal("50.00"))
                )
            ),
            isActive = false
        )

        Mockito.`when`(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
            .thenReturn(passiveRestaurant)

        assertThatThrownBy { orderApplicationService.createOrder((createOrderCommand)) }
            .isInstanceOf(OrderDomainException::class.java)
            .hasMessage("Restaurant with id $RESTAURANT_ID is currently not active!")
    }

}
