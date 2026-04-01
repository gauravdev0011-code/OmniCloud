package com.omnicloud.controllers;

import com.omnicloud.models.User;
import com.omnicloud.services.UserService;

import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public Mono<User> register(@RequestBody User user) {
        return service.register(user);
    }
}