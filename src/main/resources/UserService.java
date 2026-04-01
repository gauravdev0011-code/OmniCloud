package com.omnicloud.services;

import com.omnicloud.models.User;
import com.omnicloud.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> register(User user) {

        String hashedPassword =
                encoder.encode(user.getPassword());

        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public Mono<User> login(String email, String password) {

        return userRepository.findByEmail(email)
                .filter(user ->
                        encoder.matches(password,
                                user.getPassword()));
    }
}