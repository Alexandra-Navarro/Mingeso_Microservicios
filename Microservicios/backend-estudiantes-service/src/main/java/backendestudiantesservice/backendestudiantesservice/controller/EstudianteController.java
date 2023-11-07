package backendestudiantesservice.backendestudiantesservice.controller;

import backendestudiantesservice.backendestudiantesservice.entity.EstudianteEntity;
import backendestudiantesservice.backendestudiantesservice.service.EstudianteService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {
    @Autowired
    private EstudianteService estudianteService;

    // Listar estudiantes de la base de datos
    @GetMapping("/registro/listar")
    public ResponseEntity<List<EstudianteEntity>> listar() {
        List<EstudianteEntity> estudiantes = estudianteService.obtenerEstudiantes();
        return new ResponseEntity<>(estudiantes, HttpStatus.OK);
    }

    // Formulario de registro de los alumnos (No es necesario para Postman)

    // Guardar los estudiantes que se ingresan a la base de datos
    @PostMapping("/registro/listar")
    public ResponseEntity<EstudianteEntity> guardarEstudiante(@RequestBody EstudianteEntity estudiante) {
        EstudianteEntity nuevoEstudiante = estudianteService.guardarEstudiante(estudiante);
        return new ResponseEntity<>(nuevoEstudiante, HttpStatus.CREATED);
    }

    // Opción para eliminar un estudiante
    @DeleteMapping("/registro/listar/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) {
        estudianteService.eliminarEstudiantePorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
