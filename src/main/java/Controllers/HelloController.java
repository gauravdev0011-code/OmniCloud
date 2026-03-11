package com.omnicloud.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Mono<String> hello() {
        // Spicy addition: timestamp + small motivational message
        return Mono.just("OmniCloud Backend Ready! 🚀 Time: " + System.currentTimeMillis());
    }
}