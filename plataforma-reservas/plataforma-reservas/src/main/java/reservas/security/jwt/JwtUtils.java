package reservas.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Componente de utilidad para la gestión de JSON Web Tokens (JWT).
 * <p>
 * Se encarga de generar, firmar y validar los tokens de autenticación,
 * así como de extraer la información contenida en ellos (claims).
 * </p>
 */
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private int jwtExpirationMs;

    private SecretKey key;

    /**
     * Inicializa la clave criptográfica una vez que las propiedades han sido inyectadas.
     * Utiliza codificación UTF-8 para garantizar consistencia en la generación de la clave.
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un nuevo token JWT para un usuario autenticado.
     *
     * @param username El nombre de usuario (subject) para el token.
     * @param role El rol del usuario, que se incluirá como un "claim" personalizado.
     * @return String La cadena del token JWT firmado y compacto.
     */
    public String generateJwtToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    /**
     * Extrae el nombre de usuario (subject) contenido en un token JWT.
     *
     * @param token El token JWT del cual extraer la información.
     * @return String El nombre de usuario contenido en el token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Valida la firma y la estructura de un token JWT.
     *
     * @param authToken El token a validar.
     * @return boolean {@code true} si el token es válido y no ha expirado; {@code false} en caso contrario.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(authToken);
            return true;
        } catch (Exception e) {
            // Se recomienda agregar logs aquí para depurar errores de seguridad
            return false;
        }
    }
}