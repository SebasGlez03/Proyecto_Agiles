/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package reservas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import reservas.model.Experience;

/**
 *
 * @author Beto_
 */
public interface ExperienceRepository extends JpaRepository<Experience, Long>{
    List<Experience> findByEmprendedor_Username(String username);
}
