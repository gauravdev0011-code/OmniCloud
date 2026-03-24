package com.omnicloud.services;

import com.omnicloud.models.User;
import com.omnicloud.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<User> register(User user) {

        user.setPassword(
                encoder.encode(user.getPassword())
        );

        return repository.save(user);
    }

    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}