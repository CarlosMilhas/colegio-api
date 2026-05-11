package com.milhas.colegio.config;

import com.milhas.colegio.model.Role;
import com.milhas.colegio.model.User;
import com.milhas.colegio.repository.UserRepository;
import com.milhas.colegio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void run(String... args) {


        if (userRepository.count() == 0) {
            userService.createUserAccount(new User("admin", "admin123", Role.ADMIN));
        }
    }
}