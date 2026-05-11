package com.milhas.colegio.service;


import com.milhas.colegio.model.User;
import com.milhas.colegio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User createUserAccount(User user) {
        if (userRepository.findByUsername(user.getUsername())
                          .isPresent()) {
            throw new IllegalArgumentException("The username is already taken.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);


    }
}
