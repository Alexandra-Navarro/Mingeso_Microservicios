package backendestudiantesservice.backendestudiantesservice.repository;

import backendestudiantesservice.backendestudiantesservice.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {
    EstudianteEntity findByRut(String rut);
}