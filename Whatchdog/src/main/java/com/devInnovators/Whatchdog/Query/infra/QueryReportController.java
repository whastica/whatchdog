package com.devInnovators.Whatchdog.Query.infra;


import com.devInnovators.Whatchdog.Query.application.interfaces.QueryReportServiceInterface;

import com.devInnovators.Whatchdog.Query.domain.model.QueryStatus;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/reports")
public class QueryReportController {

    private final QueryReportServiceInterface reportService;

    public QueryReportController(QueryReportServiceInterface reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{idReport}")
    public ResponseEntity<ReportDTO> getReporteById(@PathVariable String idReport) {
        ReportDTO reporte = reportService.findByIdReport(idReport); // Si no se encuentra, lanzará una excepción
        return ResponseEntity.ok(reporte); // Retorna 200 OK con el reporte
    }
    @GetMapping
    public ResponseEntity<List<ReportDTO>> getReportes() {
        List<ReportDTO> reportes = reportService.findAllReports();
        return ResponseEntity.ok(reportes); // Retorna 200 OK con la lista de reportes
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReportDTO>> getReportesByStatus(@PathVariable QueryStatus status) {
        List<ReportDTO> reportes = reportService.findReportsByStatus(status);
        return ResponseEntity.ok(reportes); // Retorna 200 OK con la lista de reportes
    }

    @GetMapping("/category/{categoryIssue}")
    public ResponseEntity<List<ReportDTO>> getReportesByCategoryIssue(@PathVariable String categoryIssue) {
        List<ReportDTO> reportes = reportService.getReportsByCategoryIssue(categoryIssue);
        return ResponseEntity.ok(reportes); // Retorna 200 OK con la lista de reportes
    }

    @GetMapping("/admin/{idAdminC}")
    public ResponseEntity<List<ReportDTO>> getReportesByAdminId(@PathVariable String idAdminC) {
        // Obtiene los reportes usando el servicio
        List<ReportDTO> reportes = reportService.getReportsByAdminId(idAdminC);
        
        // Retorna 200 OK con la lista de reportes
        return ResponseEntity.ok(reportes);
    }

   /*   //Actualiza el estado de un reporte y disparar el evento de revision
    @PutMapping("/status")
    public ResponseEntity<Void> updateReportStatus(@RequestBody RevisedReportEvent revisedReportEvent) {
        reportService.updateReportStatus(revisedReportEvent);
        return ResponseEntity.noContent().build(); // 204 No Content
    }  */



}