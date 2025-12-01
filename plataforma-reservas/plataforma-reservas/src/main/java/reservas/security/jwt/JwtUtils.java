/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reservas.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private int jwtExpirationMs;

    private SecretKey key;

    // Inicializa la llave una sola vez después de cargar la configuración
    @PostConstruct
    public void init() {
        // En JJWT 0.12+, se recomienda usar StandardCharsets.UTF_8 explícitamente
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key) // Firma automática con el algoritmo adecuado según la llave
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(key) // <--- NUEVA SINTAXIS
                .build()
                .parseSignedClaims(token) // Reemplaza a parseClaimsJws
                .getPayload()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                .verifyWith(key) // <--- NUEVA SINTAXIS
                .build()
                .parseSignedClaims(authToken);
            return true;
        } catch (Exception e) {
            // Puedes agregar logs aquí: logger.error("Invalid JWT signature: {}", e.getMessage());
            return false;
        }
    }
}