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
@RequestMapping("/cuotas")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoRepository pagoRepository;

    @GetMapping("/{rut}")
    public ResponseEntity<?> verCuotasEstudiante(@PathVariable String rut) {
        EstudianteEntity estudiante = pagoService.buscarEstudiantePorRut(rut);

        if (estudiante != null) {
            String[] estados_cuotas = pagoService.listarEstado(estudiante);
            List<PagoEntity> pagos = new ArrayList<>();
            double[] cuotas = pagoService.listarCuotas(estudiante);
            String[] fechas_limite = pagoService.calcularFechasLimitePago(estudiante);
            String[] meses_atraso = pagoService.calcularMesesAtraso(estudiante, fechas_limite);
            double[] cuotas_finales = new double[cuotas.length];

            for (int i = 0; i < cuotas.length; i++) {
                cuotas_finales[i] = pagoService.calcularCuotaFinal(estudiante, Integer.parseInt(meses_atraso[i]), cuotas[i]);

                PagoEntity pago = new PagoEntity();
                pago.setMatricula(estudiante.getRut());
                pago.setFecha_limite_pago(LocalDate.parse(fechas_limite[i]));
                pago.setMeses_atraso(Integer.parseInt(meses_atraso[i]));
                pago.setValor_cuota(cuotas[i]);
                pago.setValor_cuota_final(cuotas_finales[i]);
                pago.setCantidad_cuotasp(i + 1);
                pago.setEstado_cuota(estados_cuotas[i]);
                pagos.add(pago);
            }

            if (pagos.isEmpty()){
                pagoRepository.saveAll(pagos);
            }

            List<String> estados_cuotas_final = pagoService.obtenerEstadosDeCuotasPorRut(rut);

            // Construir un objeto JSON de respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("estados_cuotas_final", estados_cuotas_final);
            response.put("estudiante", estudiante);
            response.put("cuotas", cuotas);
            response.put("cuotas_finales", cuotas_finales);
            response.put("fechas_limite", fechas_limite);
            response.put("meses_atraso", meses_atraso);
            response.put("estados_cuotas", estados_cuotas);
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
                pago.setEstado_cuota("Pagada");
                pagoRepository.save(pago);

                return ResponseEntity.ok("Cuota pagada exitosamente");
            } else {
                return ResponseEntity.ok("Índice de cuota inválido");
            }
        }

        return ResponseEntity.ok("Estudiante no encontrado");
    }
}