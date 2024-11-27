package com.devInnovators.Whatchdog.Query.application.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devInnovators.Whatchdog.Command.exception.ResourceNotFoundException;
import com.devInnovators.Whatchdog.Query.application.DTO.CommentDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Query.application.EventsDTO.RevisedReportEvent;
import com.devInnovators.Whatchdog.Query.application.interfaces.QueryReportServiceInterface;
import com.devInnovators.Whatchdog.Query.domain.model.QueryComment;
import com.devInnovators.Whatchdog.Query.domain.model.QueryReport;
import com.devInnovators.Whatchdog.Query.domain.model.QueryStatus;
import com.devInnovators.Whatchdog.Query.domain.repository.QueryReportRepository;

@Service
public class QueryReportServiceImpl implements QueryReportServiceInterface {

 
    private final QueryReportRepository reportRepository;



    @Autowired
 
    public QueryReportServiceImpl(QueryReportRepository reportRepository) {
        this.reportRepository = reportRepository;
       
    }
    
 
    public ReportDTO  findByIdReport(String idReport ){
        QueryReport queryReport = reportRepository.findByIdReport(idReport);
        if (queryReport == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found with ID: " + idReport);
        }
        return convertReportToDTO(queryReport);
    }
  
    public List<ReportDTO> findAllReports() {
        List<QueryReport> reports = reportRepository.findAll();
        
        if (reports.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reports found");
            
        }
        return reports.stream()
                      .map(this::convertReportToDTO)
                      .collect(Collectors.toList());
    }

    @Override 
    public List<ReportDTO> findReportsByStatus(QueryStatus status) {
        List<QueryReport> reports = reportRepository.findByStatus(status);
        if (reports.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reports found with status: " + status);
        }
        return reports.stream()
                      .map(this::convertReportToDTO)
                      .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByCategoryIssue(String categoryIssue) {
        List<QueryReport> reports = reportRepository.findByCategoryIssue(categoryIssue);
        if (reports.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reports found with category issue: " + categoryIssue);
        }
        return reports.stream()
                      .map(this::convertReportToDTO)
                      .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByAdminId(String idAdminC) {
        if (idAdminC == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin ID cannot be null");
        }

        // Log para depuración
        System.out.println("Consultando reports con admin ID: " + idAdminC);
        
        List<QueryReport> reports = reportRepository.findByIdAdminC(idAdminC);
         // Log para depuración
        

        System.out.println("Número de reports encontrados con MongoTemplate: " + reports.size());

        if (reports.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reports found with admin ID: " + idAdminC);
        }

        return reports.stream()
                .map(this::convertReportToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateReportStatus(RevisedReportEvent revisedReportEvent) {
        // Buscar el reporte en la base de datos usando el ID del evento
        QueryReport queryReport = reportRepository.findByIdReport(revisedReportEvent.getId());
        if (queryReport == null) {
            throw new ResourceNotFoundException("Reporte no encontrado con id: " + revisedReportEvent.getId());
        }

        // Actualizar el estado y otros atributos relevantes
        queryReport.setStatus(revisedReportEvent.getStatus());
        queryReport.setCategoryIssue(revisedReportEvent.getCategoryIssue().toString());
        queryReport.setUpdateDate(revisedReportEvent.getUpdateDate());
        queryReport.setIdissue(revisedReportEvent.getIssueId());

        // Guardar los cambios en la base de datos
        reportRepository.save(queryReport);
    } 

    // Método privado para la conversión de Report a ReportDTO
    private ReportDTO convertReportToDTO(QueryReport report) {  
        // Manejo seguro de nulos para obtener los IDs
       // String citizenId = (report.getIdcitizen() != null && report.getIdcitizen().getId() != null) ? report.getIdcitizen().getId() : null;
       // String issueId = (report.getIdissue() != null && report.getIdissue().getId() != null) ? report.getIdissue().getId() : null;
        //String adminId = (report.getIdAdminC() != null && report.getIdAdminC().getId() != null) ? report.getIdAdminC().getId() : null;

        // Conversión de comentarios, si existe
        List<CommentDTO> commentDTOs = report.getComments() != null ? convertCommentsToDTOs(report.getComments()) : new ArrayList<>();

        return new ReportDTO(
                report.getIdReport(),
                report.getDescription(),
                report.getIdcitizen(),
                report.getIdissue(),
                report.getIdAdminC(),
                commentDTOs,
                report.getStatus(),
                report.getCategoryIssue(),
                report.getCoordinates(),
                report.getCreateDate(),
                report.getUpdateDate(),
                report.getFotoUrl(),
                report.getNumLikes(),
                report.getNumDislikes()
        );
       
    }

  

    private CommentDTO convertCommentToDTO(QueryComment comment) {
           // Obtén solo el ID del ciudadano como String
        String citizenId = comment.getIdcitizen() != null ? comment.getIdcitizen().getId() : null;

        // Verifica si el ID del reporte también no es null
        String reportId = comment.getIdreport() != null ? comment.getIdreport().getIdReport() : null;

        // Crea el CommentDTO usando solo los datos necesarios
        return new CommentDTO(
                comment.getId(),
                comment.getDescription(),
                citizenId,  // Aquí pasamos solo el ID del ciudadano
                comment.getCreateDate(),
                reportId
        );
    }
    private List<CommentDTO> convertCommentsToDTOs(List<QueryComment> comments) {
        return comments.stream()
                .map(this::convertCommentToDTO) // Utiliza el método que convierte un solo Comment a CommentDTO
                .collect(Collectors.toList());
    }
    

}
