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
 * Controlador REST para el manejo de recursos de Experiencias.
 * Expone endpoints bajo la ruta "/api/experiences".
 */
@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {
    
    @Autowired
    private ExperienceService experienceService;

    /**
     * Obtiene el listado completo de experiencias disponibles.
     * Endpoint: GET /api/experiences
     * * @return ResponseEntity con la lista de experiencias.
     */
    @GetMapping
    public ResponseEntity<List<Experience>> listAll() {
        return ResponseEntity.ok(experienceService.getAllExperiences());
    }

    /**
     * Crea una nueva experiencia. Requiere que el usuario esté autenticado.
     * El usuario propietario se infiere del Token JWT actual.
     * Endpoint: POST /api/experiences
     *
     * @param experience El objeto Experience enviado en el cuerpo de la petición.
     * @return ResponseEntity con la experiencia creada o un mensaje de error (Bad Request).
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Experience experience) {
        try {
            // Obtenemos el usuario del contexto de seguridad (inyectado por JwtAuthFilter)
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            Experience newExp = experienceService.createExperience(experience, username);
            return ResponseEntity.ok(newExp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}