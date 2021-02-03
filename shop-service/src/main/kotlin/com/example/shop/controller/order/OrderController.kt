package com.example.shop.controller.order

import com.example.shop.dto.response.OrderResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController {

    @GetMapping
    fun getOrders(): List<OrderResponse> {
        return listOf(
                OrderResponse(orderId=1, name = "orderName"),
                OrderResponse(orderId=2, name = "orderName"),
                OrderResponse(orderId=3, name = "orderName")
        )
    }

    @GetMapping("/{orderId}")
    fun getOrder(
            @PathVariable orderId: Long
    ): OrderResponse {
        return OrderResponse(orderId=orderId, name = "orderName")
    }
}