package com.milhas.colegio.controller;

import com.milhas.colegio.dto.requests.DirectorRegistrationDTO;
import com.milhas.colegio.model.User;
import com.milhas.colegio.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/directors")
@RequiredArgsConstructor
public class DirectorController {

    public final DirectorService directorService;

    @PostMapping("/register")
    public ResponseEntity<?> registerDirector(@RequestBody DirectorRegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(registrationDTO.getPassword());
        user.setRole(com.milhas.colegio.model.Role.ADMIN);
        directorService.createDirector(user);
        return ResponseEntity.ok("Director registered successfully");
    }
}
