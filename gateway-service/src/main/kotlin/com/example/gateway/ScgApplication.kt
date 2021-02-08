package com.example.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import reactor.core.publisher.Mono





@SpringBootApplication
@EnableEurekaClient
class ScgApplication

fun main(args: Array<String>) {
	runApplication<ScgApplication>(*args)

	fun userKeyResolver(): KeyResolver {
		return KeyResolver { e -> Mono.just(e.request.headers.getFirst("X-Request-Auth") ?: "") }
	}
}