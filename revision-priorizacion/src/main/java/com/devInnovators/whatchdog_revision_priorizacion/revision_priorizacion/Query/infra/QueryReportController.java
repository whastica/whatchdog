package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.infra;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.DTO.ReportDTO;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.interfaces.QueryReportServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/query/report")
public class QueryReportController {

    @Autowired
    private QueryReportServiceInterface reportService;

    @GetMapping
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable String reportId) {
        return ResponseEntity.ok(reportService.getReportById(reportId));
    }

    @GetMapping("/issue/{issueId}")
    public ResponseEntity<List<ReportDTO>> getReportsByIssueId(@PathVariable String issueId) {
        return ResponseEntity.ok(reportService.getReportsByIssueId(issueId));
    }
}
