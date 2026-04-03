package com.omnicloud.exceptions.controllers;

import com.omnicloud.models.User;
import com.omnicloud.services.UserService;

import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register user
    @PostMapping("/register")
    public Mono<User> register(@RequestBody User user) {
        return userService.register(user);
    }

    // Login user
    @PostMapping("/login")
    public Mono<User> login(@RequestBody User request) {
        return userService.login(
                request.getEmail(),
                request.getPassword()
        );
    }
}