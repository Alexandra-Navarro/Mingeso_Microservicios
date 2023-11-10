package backendcuotasservice.backendcuotasservice.controller;


import backendcuotasservice.backendcuotasservice.entity.PagoEntity;
import backendcuotasservice.backendcuotasservice.model.EstudianteEntity;
import backendcuotasservice.backendcuotasservice.repository.PagoRepository;
import backendcuotasservice.backendcuotasservice.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/cuotas") // Cambiado el path para indicar que es una API
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoRepository pagoRepository;

    @GetMapping("/{rut}")
    public ResponseEntity<?> verCuotasEstudiante(@PathVariable String rut) {
        EstudianteEntity estudiante = pagoService.buscarEstudiantePorRut(rut);

        if (estudiante != null) {
            String[] estadosCuotas = pagoService.listarEstado(estudiante);
            List<PagoEntity> pagos = new ArrayList<>();
            double[] cuotas = pagoService.listarCuotas(estudiante);
            String[] fechasLimite = pagoService.calcularFechasLimitePago(estudiante);
            String[] mesesAtraso = pagoService.calcularMesesAtraso(estudiante, fechasLimite);
            double[] cuotasFinales = new double[cuotas.length];

            for (int i = 0; i < cuotas.length; i++) {
                cuotasFinales[i] = pagoService.calcularCuotaFinal(estudiante, Integer.parseInt(mesesAtraso[i]), cuotas[i]);

                PagoEntity pago = new PagoEntity();
                pago.setMatricula(estudiante.getRut());
                pago.setFechaLimitePago(LocalDate.parse(fechasLimite[i]));
                pago.setMesesAtraso(Integer.parseInt(mesesAtraso[i]));
                pago.setValorCuota(cuotas[i]);
                pago.setValorCuotaFinal(cuotasFinales[i]);
                pago.setCantidadCuotasP(i + 1);
                pago.setEstadoCuota(estadosCuotas[i]);
                pagos.add(pago);
            }

            if (pagos.isEmpty()){
                pagoRepository.saveAll(pagos);
            }

            List<String> estadosCuotasFinal = pagoService.obtenerEstadosDeCuotasPorRut(rut);

            // Construir un objeto JSON de respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("estadosCuotasFinal", estadosCuotasFinal);
            response.put("estudiante", estudiante);
            response.put("cuotas", cuotas);
            response.put("cuotasFinales", cuotasFinales);
            response.put("fechasLimite", fechasLimite);
            response.put("mesesAtraso", mesesAtraso);
            response.put("estadosCuotas", estadosCuotas);
            response.put("pagos", pagos);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok("Estudiante no encontrado");
    }

    @PostMapping("/{rut}/pagar/{cuotaIndex}")
    public ResponseEntity<?> pagarCuota(@PathVariable String rut, @PathVariable int cuotaIndex) {
        EstudianteEntity estudiante = pagoService.buscarEstudiantePorRut(rut);

        if (estudiante != null && cuotaIndex >= 0 && cuotaIndex < estudiante.getCantidad_cuotase()) {
            List<PagoEntity> pagos = pagoService.obtenerPagosPorMatricula(rut);

            if (cuotaIndex < pagos.size()) {
                PagoEntity pago = pagos.get(cuotaIndex);
                pago.setEstadoCuota("Pagada");
                pagoRepository.save(pago);

                return ResponseEntity.ok("Cuota pagada exitosamente");
            } else {
                return ResponseEntity.ok("Índice de cuota inválido");
            }
        }

        return ResponseEntity.ok("Estudiante no encontrado");
    }
}




