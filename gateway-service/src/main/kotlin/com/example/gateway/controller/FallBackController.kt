package com.example.gateway.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/fallback")
class FallBackController {

    @GetMapping("/message")
    fun test(): String {
        return "This is fallback message"
    }
}