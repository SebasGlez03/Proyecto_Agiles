/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reservas.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reservas.dto.JwtResponse;
import reservas.dto.LoginRequest;
import reservas.dto.RegistrationRequest;
import reservas.service.AuthService;

/**
 *
 * @author Beto_
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {
        
        // Usamos request.getUsername()
        String username = request.getUsername(); 
        String password = request.getPassword();
        String role = request.getRole() != null ? request.getRole() : "VISITANTE"; 

        try {
            authService.registerNewUser(username, password, role);
            return new ResponseEntity<>("Registro exitoso.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Endpoint para iniciar sesión y obtener un token JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        
        Optional<JwtResponse> jwtResponse = authService.authenticateUser(loginRequest);

        if (jwtResponse.isPresent()) {
            // Éxito: Devuelve el token y los datos del usuario
            return ResponseEntity.ok(jwtResponse.get());
        } else {
            // Fallo: Devuelve un error 401 Unauthorized
            return ResponseEntity.status(401).body("{\"message\": \"Nombre de usuario o contraseña incorrectos.\"}");
        }
    }
    
    @GetMapping()
    public ResponseEntity<String> hola(){
        return new ResponseEntity<>("Holaaaaaaaaaa.", HttpStatus.CREATED);
    }
    
}
