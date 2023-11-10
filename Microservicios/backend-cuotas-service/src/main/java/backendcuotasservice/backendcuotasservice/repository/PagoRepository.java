package backendcuotasservice.backendcuotasservice.repository;

import backendcuotasservice.backendcuotasservice.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository< PagoEntity, Long> {
    List<PagoEntity> findByMatricula(String rut);
}
