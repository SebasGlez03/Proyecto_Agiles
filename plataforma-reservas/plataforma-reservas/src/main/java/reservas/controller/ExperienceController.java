/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reservas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reservas.model.Experience;
import reservas.service.ExperienceService;

/**
 *
 * @author Beto_
 */
@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {
    @Autowired
    private ExperienceService experienceService;

    // R6: Listar todas (Público o logueado)
    @GetMapping
    public ResponseEntity<List<Experience>> listAll() {
        return ResponseEntity.ok(experienceService.getAllExperiences());
    }

    // R4 y R5: Crear experiencia (Solo autenticados)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Experience experience) {
        try {
            // Obtenemos el usuario del Token JWT automáticamente gracias al filtro
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            Experience newExp = experienceService.createExperience(experience, username);
            return ResponseEntity.ok(newExp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
