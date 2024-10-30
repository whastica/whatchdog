package com.devInnovators.Whatchdog.Query.infra;


import com.devInnovators.Whatchdog.Query.application.interfaces.QueryReportServiceInterface;

import com.devInnovators.Whatchdog.Query.domain.model.Status;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReportDTO>> getReportesByStatus(@PathVariable Status status) {
        List<ReportDTO> reportes = reportService.findReportsByStatus(status);
        return ResponseEntity.ok(reportes); // Retorna 200 OK con la lista de reportes
    }

    @GetMapping("/category/{categoryIssue}")
    public ResponseEntity<List<ReportDTO>> getReportesByCategoryIssue(@PathVariable String categoryIssue) {
        List<ReportDTO> reportes = reportService.getReportsByCategoryIssue(categoryIssue);
        return ResponseEntity.ok(reportes); // Retorna 200 OK con la lista de reportes
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<ReportDTO>> getReportesByAdminId(@PathVariable String adminId) {
        // Obtiene los reportes usando el servicio
        List<ReportDTO> reportes = reportService.getReportsByAdminId(adminId);
        
        // Retorna 200 OK con la lista de reportes
        return ResponseEntity.ok(reportes);
    }


}