/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reservas.dto;

/**
 *
 * @author Beto_
 */
public class LoginRequest {
    // Este campo se mapeará al 'username' del formulario
    private String username;
    private String password;

    // Getters y Setters (Obligatorios si no usas Lombok)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
