package com.devInnovators.Whatchdog.Command.infra;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.CommentDTO;
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
        // Verificar que el idReport no sea nulo
        if (reportDTO.getIdReport() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idReport is required");
        }

        // Crear el reporte utilizando el servicio
        ReportDTO createdReport = reportService.createReport(reportDTO);

        // Retornar la respuesta con el reporte creado
        return ResponseEntity.status(201).body(createdReport);  // 201 Created
    }

    @PutMapping("/{idReport}")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable String idReport, @RequestBody ReportDTO reportDTO) {
        ReportDTO updatedReport = reportService.updateReport(idReport, reportDTO);
        return ResponseEntity.ok(updatedReport); // 200 OK
    }

    @DeleteMapping("/{idReport}")
    public ResponseEntity<Void> deleteReport(@PathVariable String idReport) {
        reportService.deleteReport(idReport);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
    @PostMapping("/{idReport}/comments")
    public ResponseEntity<CommentDTO> createCommentToReport( @RequestBody CommentDTO commentDTO, @PathVariable String idReport) {
        CommentDTO createdComment = reportService.createCommentToReport(commentDTO, idReport);
        return ResponseEntity.status(201).body(createdComment); // 201 Created
    }
    @PutMapping("/{idReport}/comments/{idComment}")
    public ResponseEntity<Void> addCommentToReport(@PathVariable String idReport, @PathVariable String idComment) {
        reportService.addCommentToReport(idReport, idComment);
        return ResponseEntity.ok().build(); // 200 OK
    }

}
