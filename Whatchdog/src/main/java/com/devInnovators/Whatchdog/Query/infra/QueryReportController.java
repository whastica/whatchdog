package com.devInnovators.Whatchdog.Query.infra;


import com.devInnovators.Whatchdog.Query.application.interfaces.QueryReportServiceInterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devInnovators.Whatchdog.Query.domain.model.Status;
import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;


@RestController
@RequestMapping("/api/reports")
public class QueryReportController {

    private final QueryReportServiceInterface reportService;

    @Autowired
    public QueryReportController(QueryReportServiceInterface reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getReporteById(@PathVariable String id) {
        ReportDTO reporte = reportService.findReportById(id); // Si no se encuentra, lanzará una excepción
        return ResponseEntity.ok(reporte); // Retorna 200 OK con el reporte
    }
    @GetMapping
    public ResponseEntity<List<ReportDTO>> getReportes() {
        List<ReportDTO> reportes = reportService.findAllReports();
        return ResponseEntity.ok(reportes); // Retorna 200 OK con la lista de reportes
    }
   /*  // Endpoint para obtener un reporte por su estado
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReportDTO>> getReporteByStatus(@PathVariable String status) {
        Status statusEnum;
        try {
            // Convertir el String a un enum Status
            statusEnum = Status.valueOf(status); // Lanza IllegalArgumentException si el String no es un valor válido
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // Maneja el caso donde el String no es válido
        }

        // Llamada correcta al servicio
        List<ReportDTO> reportes = reportService.findReportByEstado(statusEnum);
        return ResponseEntity.ok(reportes); // Devuelve la lista de reportes
    }
    

    // Endpoint para obtener un reporte por ciudadano
    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<List<ReportDTO>> getReporteByCitizen(@PathVariable String id) {
        List<ReportDTO> reporte = reportService.findReportByCitizenId(id);
        if (reporte != null) {
            return ResponseEntity.ok(reporte);  // Retorna 200 OK con el reporte
        } else {
            return ResponseEntity.notFound().build();  // Retorna 404 si no encuentra el reporte
        }
    } */


}