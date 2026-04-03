package com.omnicloud.exceptions.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("OmniCloud Backend Ready! 🚀 Time: " + System.currentTimeMillis());
    }
}
