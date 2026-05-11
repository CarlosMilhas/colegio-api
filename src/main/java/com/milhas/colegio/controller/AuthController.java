package com.milhas.colegio.controller;

import com.milhas.colegio.dto.requests.LoginRequest;
import com.milhas.colegio.dto.responses.AuthResponse;
import com.milhas.colegio.security.JwtService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        // 1. Delegar verificación de credenciales al proveedor configurado
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. Recuperar entidad del usuario validado
        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

        // 3. Generar token criptográfico
        String jwtToken = jwtService.generateToken(user);

        // 4. Retornar payload con el token
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }
}

