/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reservas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservas.model.Experience;
import reservas.model.User;
import reservas.repository.ExperienceRepository;
import reservas.repository.UserRepository;

/**
 *
 * @author Beto_
 */
@Service
public class ExperienceService {
    @Autowired
    private ExperienceRepository experienceRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    public Experience createExperience(Experience experience, String username) throws Exception {
        // Buscamos al usuario que está creando la experiencia
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // Validamos que sea EMPRENDEDOR (Regla de Negocio del PDF CU03)
        if (!"EMPRENDEDOR".equalsIgnoreCase(user.getRole())) {
            throw new Exception("Solo los emprendedores pueden crear experiencias.");
        }

        experience.setEmprendedor(user);
        return experienceRepository.save(experience);
    }
}
