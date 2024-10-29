package com.devInnovators.Whatchdog.Command.infra;

import com.devInnovators.Whatchdog.Command.aplication.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Command.aplication.DTO.CommentDTO;
import com.devInnovators.Whatchdog.Command.aplication.interfaces.CommandReviewServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/command/report")
public class CommandReportController {

    @Autowired
    private CommandReviewServiceInterface reviewService;

    // Endpoint para crear un nuevo reporte
    @PostMapping
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) {
        ReportDTO createdReport = reviewService.createReport(reportDTO);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    // Cambiar el estado del reporte a "en revision"
    @PutMapping("/{reportId}/status/review")
    public ResponseEntity<ReportDTO> changeReportStatusToReview(@PathVariable String reportId) {
        ReportDTO updatedReport = reviewService.changeStatusToReview(reportId);
        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }

    // Asignar una categoría de Issue al reporte
    @PutMapping("/{reportId}/category/{categoryIssue}")
    public ResponseEntity<ReportDTO> assignCategoryToReport(@PathVariable String reportId, @PathVariable String categoryIssue) {
        ReportDTO updatedReport = reviewService.assignCategoryToReport(reportId, categoryIssue);
        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }

    // Endpoint para actualizar un reporte existente
    @PutMapping("/{reportId}")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable String reportId, @RequestBody ReportDTO reportDTO) {
        ReportDTO updatedReport = reviewService.updateReport(reportId, reportDTO);
        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }

    // Endpoint para asignar un reporte a un Issue
    @PutMapping("/{reportId}/assignIssue/{issueId}")
    public ResponseEntity<ReportDTO> assignReportToIssue(@PathVariable String reportId, @PathVariable String issueId) {
        ReportDTO assignedReport = reviewService.assignReportToIssue(reportId, issueId);
        return new ResponseEntity<>(assignedReport, HttpStatus.OK);
    }

    // Endpoint para obtener todos los reportes asignados a un Issue
    @GetMapping("/issue/{issueId}")
    public ResponseEntity<List<ReportDTO>> getReportsByIssue(@PathVariable String issueId) {
        List<ReportDTO> reports = reviewService.getReportsByIssue(issueId);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    // Endpoint para agregar un comentario a un reporte
    @PostMapping("/{reportId}/comment")
    public ResponseEntity<CommentDTO> addCommentToReport(@PathVariable String reportId, @RequestBody CommentDTO commentDTO) {
        CommentDTO addedComment = reviewService.addCommentToReport(reportId, commentDTO);
        return new ResponseEntity<>(addedComment, HttpStatus.CREATED);
    }
}
