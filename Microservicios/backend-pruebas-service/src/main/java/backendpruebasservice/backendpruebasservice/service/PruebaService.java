package backendpruebasservice.backendpruebasservice.service;

import backendpruebasservice.backendpruebasservice.entity.PruebaEntity;
import backendpruebasservice.backendpruebasservice.repository.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<Map<String, Object>> obtenerPruebasPorFecha(LocalDate fecha) {
        List<PruebaEntity> pruebas = pruebaRepository.findByFechaExamen(fecha);

        // Convertir las pruebas a una lista de mapas con encabezados
        return pruebas.stream()
                .map(prueba -> {
                    Map<String, Object> pruebaMap = new HashMap<>();
                    pruebaMap.put("Rut Estudiante", prueba.getRut_estudiante());
                    pruebaMap.put("Asignatura del Examen", prueba.getAsignatura_examen());
                    pruebaMap.put("Fecha del Examen", prueba.getFecha_examen().toString());
                    pruebaMap.put("Puntaje Obtenido", prueba.getPuntaje_obtenido());
                    return pruebaMap;
                })
                .collect(Collectors.toList());
    }
}
