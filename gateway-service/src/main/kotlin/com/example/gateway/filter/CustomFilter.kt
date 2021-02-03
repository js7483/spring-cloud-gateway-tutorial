package com.example.gateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

open class Config(
        var baseMessage: String = ""
)

@Component
class CustomFilter: AbstractGatewayFilterFactory<Config>(Config::class.java), Ordered {

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            println("Pre CustomFilter")
            chain.filter(exchange).then(Mono.fromRunnable(Runnable { println("Post CustomFilter") }))
        }
    }

    override fun getOrder(): Int {
        return 1
    }
}