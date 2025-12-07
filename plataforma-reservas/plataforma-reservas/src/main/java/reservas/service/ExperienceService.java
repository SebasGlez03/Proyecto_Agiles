package reservas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservas.model.Experience;
import reservas.model.User;
import reservas.repository.ExperienceRepository;
import reservas.repository.UserRepository;

/**
 * Servicio de negocio para la gestión de Experiencias Turísticas.
 */
@Service
public class ExperienceService {
    
    @Autowired
    private ExperienceRepository experienceRepository;
    
    @Autowired
    private UserRepository userRepository;

    /**
     * Recupera todas las experiencias registradas en el sistema.
     *
     * @return List&lt;Experience&gt; Lista de todas las experiencias.
     */
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    /**
     * Crea una nueva experiencia asociada a un emprendedor.
     * Aplica la regla de negocio que valida que solo los usuarios con rol "EMPRENDEDOR"
     * pueden crear contenido.
     *
     * @param experience Objeto con los datos de la experiencia a crear.
     * @param username Nombre del usuario que intenta crear la experiencia.
     * @return Experience La experiencia creada y guardada.
     * @throws Exception Si el usuario no existe o no tiene el rol de "EMPRENDEDOR".
     */
    public Experience createExperience(Experience experience, String username) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // Regla de Negocio: Validación de rol
        if (!"EMPRENDEDOR".equalsIgnoreCase(user.getRole())) {
            throw new Exception("Solo los emprendedores pueden crear experiencias.");
        }

        experience.setEmprendedor(user);
        return experienceRepository.save(experience);
    }
}