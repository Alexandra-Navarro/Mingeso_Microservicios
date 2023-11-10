package backendcuotasservice.backendcuotasservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    private String matricula;
    private Double valor_cuota;
    private Double valor_cuota_final;
    private Integer cantidad_cuotasp;
    private LocalDate fecha_limite_pago;
    private LocalDate fecha_pago_cuota;
    private Integer meses_atraso;
    private String estado_cuota;
    private String estado_final;
}

