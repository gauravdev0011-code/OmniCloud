package com.omnicloud.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Mono<Map<String, Object>> health() {

        Map<String, Object> response = new HashMap<>();

        response.put("status", "UP");
        response.put("service", "OmniCloud Backend");
        response.put("time", LocalDateTime.now());
        response.put("version", "1.0.0");

        return Mono.just(response);
    }
}