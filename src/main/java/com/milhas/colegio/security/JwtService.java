package com.milhas.colegio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Clave privada estática. En producción, inyectar desde variables de entorno.
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // Extrae el nombre de usuario desde el payload del token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Fabrica un nuevo token asignando usuario, fecha de emisión, expiración y firma
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                   .setSubject(userDetails.getUsername())
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24hs
                   .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                   .compact();
    }

    // Comprueba integridad de la firma y que la fecha no haya expirado
    public boolean isTokenValid(String token,
                                UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration()
                                      .before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
