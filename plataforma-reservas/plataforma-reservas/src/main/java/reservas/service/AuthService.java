/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author Beto_
 */
@Service
public class AuthService {
    // Inyectamos el repositorio
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    
//    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
    
    /**
     * Lógica para registrar un nuevo usuario (Visitante o Emprendedor).
     */
    public User registerNewUser(String username, String rawPassword, String role) throws Exception {
        
        //1. Validamos que el username no esté duplicado
        if (userRepository.existsByUsername(username)) {
            throw new Exception("El nombre de usuario ya está en uso."); 
        }

        //2. Encriptamos la contraseña
        String encodedPassword = passwordEncoder.encode(rawPassword);

        //3. Creamos el nuevo usuario
        User newUser = new User(username, encodedPassword, role);

        //4. Guardamos en la BD (Persistencia real con JPA)
        return userRepository.save(newUser);
    }
    
    /**
     * Procesa la solicitud de inicio de sesión.
     * @param loginRequest
     * @return 
     */
    public Optional<JwtResponse> authenticateUser(LoginRequest loginRequest) {
        
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                
                // --- INTEGRACIÓN DEL JWT (CORREGIDA) ---
                String roleName = user.getRole();
                String jwtToken = jwtUtils.generateJwtToken(user.getUsername(), roleName); 
                // ----------------------------------------
                
                return Optional.of(new JwtResponse(
                    jwtToken, 
                    user.getUsername(), 
                    roleName // Usamos la variable roleName
                ));
            }
        }
        
        return Optional.empty();
    }
}
