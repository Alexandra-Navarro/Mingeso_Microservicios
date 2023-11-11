package backendpruebasservice.backendpruebasservice.controller;


import backendpruebasservice.backendpruebasservice.entity.PruebaEntity;
import backendpruebasservice.backendpruebasservice.service.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/{fecha}")
    public void exportPruebasToExcelByFecha(HttpServletResponse response, @PathVariable String fecha) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=pruebas_" + fecha + ".xlsx");

        List<PruebaEntity> pruebas = pruebaService.obtenerPruebasPorFecha(LocalDate.parse(fecha));

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Pruebas");

            // Create headers
            String[] headers = {"RUT Estudiante", "Asignatura", "Fecha de Examen", "Puntaje Obtenido"};
            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Fill data for the specific date
            int rowNum = 1;
            for (PruebaEntity prueba : pruebas) {
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(prueba.getRut_estudiante());
                row.createCell(1).setCellValue(prueba.getAsignatura_examen());
                row.createCell(2).setCellValue(prueba.getFecha_examen().toString());
                row.createCell(3).setCellValue(prueba.getPuntaje_obtenido());
            }

            workbook.write(response.getOutputStream());
        }
    }
}
