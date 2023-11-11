package backendpruebasservice.backendpruebasservice.repository;


import backendpruebasservice.backendpruebasservice.entity.PruebaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PruebaRepository extends JpaRepository<PruebaEntity, Long> {
    List<PruebaEntity> findAll();

    @Query("SELECT p FROM PruebaEntity p WHERE p.fecha_examen = :fecha")
    List<PruebaEntity> findByFechaExamen(@Param("fecha") LocalDate fecha);
}
