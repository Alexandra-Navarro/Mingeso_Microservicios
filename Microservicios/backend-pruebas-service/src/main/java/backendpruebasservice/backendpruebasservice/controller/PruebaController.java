package backendpruebasservice.backendpruebasservice.controller;


import backendpruebasservice.backendpruebasservice.entity.PruebaEntity;
import backendpruebasservice.backendpruebasservice.service.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController

@RequestMapping("/pruebas")
public class PruebaController {
    @Autowired
    private PruebaService pruebaService;




    //listar prueba
    @GetMapping()
    public ResponseEntity<List<PruebaEntity>> listar() {
        List<PruebaEntity> pruebas = pruebaService.obtenerPrueba();
        return ResponseEntity.ok(pruebas);
    }

    //ProcesarFormulario
    @PostMapping()
    public ResponseEntity<Void> procesarFormularioIngresoPruebas(@RequestBody PruebaEntity pruebasEntity) {
        pruebaService.guardarPruebas(pruebasEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/fecha")
    public ResponseEntity<Map<String, Object>> listarPorFecha(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Map<String, Object>> pruebas = pruebaService.obtenerPruebasPorFecha(fecha);

        Map<String, Object> response = new HashMap<>();
        response.put("fecha", fecha);
        response.put("encabezados", Arrays.asList("Rut Estudiante", "Asignatura del Examen", "Fecha del Examen", "Puntaje Obtenido"));
        response.put("pruebas", pruebas);

        return ResponseEntity.ok(response);
    }


}