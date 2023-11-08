package backendestudiantesservice.backendestudiantesservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    private String rut;
    private String nombre;
    private String apellidos;
    private String fecha_nacimiento;
    private String tipo_colegio_procedencia;
    private String nombre_colegio;
    private Integer anio_egreso_colegio;
    private String forma_pago;
    private Integer cantidad_cuotase;
    private String estado_matricula;
//    private List<Double> cuotas;
//    private List<String> estados_cuotas;

}