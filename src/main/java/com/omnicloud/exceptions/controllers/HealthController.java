package com.omnicloud.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health() {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "OmniCloud");
        response.put("timestamp", Instant.now().toString());

        return response;
    }
}