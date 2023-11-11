package backendpruebasservice.backendpruebasservice.service;

import backendpruebasservice.backendpruebasservice.entity.PruebaEntity;
import backendpruebasservice.backendpruebasservice.repository.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PruebaService {
    @Autowired
    private PruebaRepository pruebaRepository;

    public ArrayList<PruebaEntity> obtenerPrueba(){
        return (ArrayList<PruebaEntity>) pruebaRepository.findAll();
    }

    public void guardarPruebas(PruebaEntity pruebas) {
        pruebaRepository.save(pruebas);
    }

    public List<PruebaEntity> obtenerPruebasPorFecha(LocalDate fecha) {
        // Recupera todas las pruebas desde el repositorio
        List<PruebaEntity> todasLasPruebas = pruebaRepository.findAll();

        // Filtra las pruebas por la fecha específica
        return todasLasPruebas.stream()
                .filter(prueba -> prueba.getFecha_examen().isEqual(fecha))
                .collect(Collectors.toList());
    }
}
