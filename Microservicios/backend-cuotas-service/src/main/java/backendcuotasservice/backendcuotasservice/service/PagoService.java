package backendcuotasservice.backendcuotasservice.service;

import backendcuotasservice.backendcuotasservice.entity.PagoEntity;
import backendcuotasservice.backendcuotasservice.model.EstudianteEntity;
import backendcuotasservice.backendcuotasservice.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    RestTemplate restTemplate;

    //Aqui se calculan los descuentos correspondientes a Tipo de colegio del estudiante (PRUEBAS UNITARIAS LISTAS)
    public double calcularDescuento (EstudianteEntity estudiante){
        double arancel= 1500000;
        if (Objects.equals(estudiante.getTipo_colegio_procedencia(), "Municipal")){
            arancel = arancel - (arancel * 0.2);
        }
        if (Objects.equals(estudiante.getTipo_colegio_procedencia(), "Subvencionado")){
            arancel = arancel - (arancel * 0.1);
        }
        if (Objects.equals(estudiante.getTipo_colegio_procedencia(), "Privado")){
            arancel = arancel - (arancel * 0.0);
        }
        return arancel;
    }



    //Se calculan los descuentos según los años de egreso del colegio (PRUEBAS UNITARIAS LISTAS)
    public double calcularDescuentoEgreso (EstudianteEntity estudiante){
        double arancel = calcularDescuento(estudiante);
        if (estudiante.getAnio_egreso_colegio() < 1){
            arancel = arancel - (arancel * 0.15);
        }
        if (estudiante.getAnio_egreso_colegio() >= 1 && estudiante.getAnio_egreso_colegio() <=2 ){
            arancel = arancel - (arancel * 0.08);
        }
        if (estudiante.getAnio_egreso_colegio() >= 3 && estudiante.getAnio_egreso_colegio() <=4 ){
            arancel = arancel - (arancel * 0.04);
        }
        if (estudiante.getAnio_egreso_colegio() >= 5){
            arancel = arancel - (arancel * 0.0);
        }
        return arancel;
    }

    // Se calculan los descuentos para los que agan al contado y se verifican los maximos de cuotas por tipo de colegio (PRUEBAS UNITARIAS LISTAS)
    public double calcularCuotas(EstudianteEntity estudiante){
        double arancel = calcularDescuentoEgreso(estudiante);
        if (Objects.equals(estudiante.getForma_pago(), "Contado") && estudiante.getCantidad_cuotase()==1){
            arancel = arancel - (arancel * 0.5);
            return arancel;
        }
        if (Objects.equals(estudiante.getTipo_colegio_procedencia(), "Municipal") && estudiante.getCantidad_cuotase()<=10){
            arancel = arancel/(estudiante.getCantidad_cuotase());
        }
        if (Objects.equals(estudiante.getTipo_colegio_procedencia(), "Subvencionado") && estudiante.getCantidad_cuotase()<=7){
            arancel = arancel/(estudiante.getCantidad_cuotase());
        }
        if (Objects.equals(estudiante.getTipo_colegio_procedencia(), "Privado") && estudiante.getCantidad_cuotase()<=4){
            arancel = arancel/(estudiante.getCantidad_cuotase());
        }

        return arancel;
    }

    public double[] listarCuotas(EstudianteEntity estudiante) {
        double cuota = calcularCuotas(estudiante);
        double[] listaCuotas = new double[estudiante.getCantidad_cuotase()];

        for(int i=0 ; i < estudiante.getCantidad_cuotase(); i++){
            listaCuotas[i] = cuota;
        }
        return listaCuotas;

    }

    public String[] listarEstado(EstudianteEntity estudiante) {
        String[] listaEstados = new String[estudiante.getCantidad_cuotase()];
        for(int i=0 ; i < estudiante.getCantidad_cuotase(); i++){
            listaEstados[i]= "Pendiente";
        }
        return listaEstados;
    }


    public String[] calcularFechasLimitePago(EstudianteEntity estudiante) {
        int cantidadCuotas = estudiante.getCantidad_cuotase();
        String[] fechasLimite = new String[cantidadCuotas];

        LocalDate today = LocalDate.now();

        // Ajustar el mes inicial a marzo del año actual
        int nextMonth = 3;
        int nextYear = today.getYear();

        // Configurar la fecha límite de la primera cuota al día 10 del próximo mes
        for (int i = 0; i < cantidadCuotas; i++) {
            // Establecer la fecha límite de pago al día 10 de cada mes
            LocalDate fechaLimite = LocalDate.of(nextYear, nextMonth, 10);

            // Formatear la fecha en el formato "yyyy-MM-dd"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            fechasLimite[i] = fechaLimite.format(formatter);

            // Calcular la fecha para el próximo mes
            nextMonth++;
            if (nextMonth > 12) {
                nextMonth = 1;
                nextYear++;
            }
        }

        return fechasLimite;
    }



    public String[] calcularMesesAtraso(EstudianteEntity estudiante, String[] fechasLimite ) {
        int cantidadCuotas = estudiante.getCantidad_cuotase();
        String[] mesesAtraso = new String[cantidadCuotas];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < cantidadCuotas; i++) {

            LocalDate fechaLimite = LocalDate.parse(fechasLimite[i], formatter);
            LocalDate fechaPago = LocalDate.now();

            // Calcular la diferencia en meses entre la fecha de pago y la fecha límite
            long meses = ChronoUnit.MONTHS.between(fechaLimite, fechaPago);
            mesesAtraso[i] = String.valueOf(meses);

            // Verificar si el valor es negativo (pago antes de la fecha límite)
            if (meses < 0) {
                mesesAtraso[i] = "0";
            }
        }

        return mesesAtraso;
    }




    public double calcularCuotaFinal(EstudianteEntity estudiante, int mesesAtraso, double cuota) {
        double interes = 0;

        if (mesesAtraso == 1) {
            interes = 0.03;
        } else if (mesesAtraso == 2) {
            interes = 0.06;
        } else if (mesesAtraso == 3) {
            interes = 0.09;
        } else if (mesesAtraso > 3) {
            interes = 0.15;
        }

        return cuota + (cuota * interes);
    }

    public List<PagoEntity> obtenerPagosPorMatricula(String matricula) {
        // Utiliza el repositorio para buscar los pagos por matrícula (RUT)
        return pagoRepository.findByMatricula(matricula);
    }

    public List<String> obtenerEstadosDeCuotasPorRut(String rut) {
        List<String> estados = new ArrayList<>();
        List<PagoEntity> pagos = pagoRepository.findByMatricula(rut);

        for (PagoEntity pago : pagos) {
            estados.add(pago.getEstado_cuota());
        }

        return estados;
    }

    public EstudianteEntity buscarEstudiantePorRut(String rut) {
        System.out.println("rut: " + rut);
        ResponseEntity<EstudianteEntity> response = restTemplate.exchange(
                "http://localhost:8085/estudiante/" + rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<EstudianteEntity>() {}
        );
        return response.getBody();
    }


}



