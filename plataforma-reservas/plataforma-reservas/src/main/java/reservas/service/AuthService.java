package reservas.service;

import reservas.model.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reservas.dto.JwtResponse;
import reservas.dto.LoginRequest;
import reservas.repository.UserRepository;
import reservas.security.jwt.JwtUtils;

/**
 * Servicio encargado de la lógica de negocio relacionada con la autenticación y registro de usuarios.
 * Intermedia entre los controladores y el repositorio de usuarios.
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
    
    /**
     * Registra un nuevo usuario en la base de datos.
     * * @param username Nombre de usuario deseado.
     * @param rawPassword Contraseña en texto plano (será encriptada antes de guardar).
     * @param role Rol del usuario (ej. "VISITANTE", "EMPRENDEDOR").
     * @return User El objeto usuario persistido.
     * @throws Exception Si el nombre de usuario ya existe.
     */
    public User registerNewUser(String username, String rawPassword, String role) throws Exception {
        if (userRepository.existsByUsername(username)) {
            throw new Exception("El nombre de usuario ya está en uso."); 
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User newUser = new User(username, encodedPassword, role);

        return userRepository.save(newUser);
    }
    
    /**
     * Autentica a un usuario verificando sus credenciales y generando un token JWT.
     *
     * @param loginRequest Objeto DTO con el usuario y contraseña.
     * @return Optional&lt;JwtResponse&gt; Contenedor con la respuesta JWT si las credenciales son válidas,
     * u Optional vacío si fallan.
     */
    public Optional<JwtResponse> authenticateUser(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String roleName = user.getRole();
                String jwtToken = jwtUtils.generateJwtToken(user.getUsername(), roleName); 
                
                return Optional.of(new JwtResponse(
                    jwtToken, 
                    user.getUsername(), 
                    roleName
                ));
            }
        }
        return Optional.empty();
    }
}