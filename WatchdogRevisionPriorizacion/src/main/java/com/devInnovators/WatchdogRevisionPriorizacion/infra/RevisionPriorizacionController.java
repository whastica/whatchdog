package com.devInnovators.WatchdogRevisionPriorizacion.infra;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devInnovators.WatchdogRevisionPriorizacion.application.DTO.IssueDTO;
import com.devInnovators.WatchdogRevisionPriorizacion.application.DTO.ReportDTO;
import com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO.RevisedReportEvent;

import com.devInnovators.WatchdogRevisionPriorizacion.application.interfaces.RevisionPriorizacionServiceInterface;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Priority;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.StatusIssue;

@RestController
@RequestMapping("/api/v1/issues")
public class RevisionPriorizacionController {

    private final RevisionPriorizacionServiceInterface revisionPriorizacionService;

    // Constructor
    public RevisionPriorizacionController(RevisionPriorizacionServiceInterface revisionPriorizacionService) {
        this.revisionPriorizacionService = revisionPriorizacionService;
    }

    // Crear un nuevo Issue
    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueDTO issueDTO) {
        IssueDTO createdIssue = revisionPriorizacionService.createIssue(issueDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIssue);
    }

    // Actualizar el estado de un Issue
    @PutMapping("/{issueId}/status")
    public ResponseEntity<IssueDTO> updateIssueStatus(@PathVariable String issueId, @RequestParam StatusIssue status) {
        IssueDTO updatedIssue = revisionPriorizacionService.updateIssueStatus(issueId, status);
        return ResponseEntity.ok(updatedIssue);
    }

    // Obtener todos los Issues
    @GetMapping
    public ResponseEntity<List<IssueDTO>> getAllIssues() {
        List<IssueDTO> issues = revisionPriorizacionService.getAllIssues();
        return ResponseEntity.ok(issues);
    }

    // Priorizar reportes
    @PostMapping("/reports/prioritize")
    public ResponseEntity<List<ReportDTO>> prioritizeReports(@RequestBody List<ReportDTO> reports, @RequestParam Priority priority) {
        List<ReportDTO> prioritizedReports = revisionPriorizacionService.prioritizeReports(reports, priority);
        return ResponseEntity.ok(prioritizedReports);
    }

    // Revisar un reporte
    @PutMapping("/reports/{reportId}")
    public ResponseEntity<ReportDTO> reviseReport(@PathVariable String reportId, @RequestBody RevisedReportEvent event) {
        revisionPriorizacionService.RevisedReport(event);
        return ResponseEntity.ok().build();
    }
}

