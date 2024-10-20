package com.devInnovators.Whatchdog.Command.infra;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.interfaces.CommandReportServiceInterface;

@RestController
@RequestMapping("/api/reports")
public class CommandReportController {

    private final CommandReportServiceInterface reportService;

    public CommandReportController(CommandReportServiceInterface reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) {
        ReportDTO createdReport = reportService.createReport(reportDTO);
        return ResponseEntity.status(201).body(createdReport); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable String id, @RequestBody ReportDTO reportDTO) {
        ReportDTO updatedReport = reportService.updateReport(id, reportDTO);
        return ResponseEntity.ok(updatedReport); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable String id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
