package backendpruebasservice.backendpruebasservice.repository;


import backendpruebasservice.backendpruebasservice.entity.PruebaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PruebaRepository {
    List<PruebaEntity> findAll();

    void save(PruebaEntity pruebas);
}
