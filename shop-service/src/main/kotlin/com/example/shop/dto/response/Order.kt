package com.example.shop.dto.response

data class OrderResponse(
        val orderId: Long,
        val name: String
)

data class OrdersResponse(
        val orders: List<OrdersResponse>
)
