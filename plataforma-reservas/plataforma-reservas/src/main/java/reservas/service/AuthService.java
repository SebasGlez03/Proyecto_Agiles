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
import reservas.repository.UserRepository;

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
     * Lógica para la autenticación de usuarios (R2 - Login).
     */
    public User authenticateUser(String username, String rawPassword) throws Exception {
        // Buscar por username en la BD
        User user = Optional.ofNullable(userRepository.findByUsername(username))
                  .orElseThrow(() -> new Exception("Usuario no encontrado."));

        // Verificamos la contraseña
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new Exception("Credenciales incorrectas.");
        }

        return user;
    }
}
