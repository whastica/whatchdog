package com.devInnovators.Whatchdog.Query.application.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devInnovators.Whatchdog.Query.application.DTO.AdminDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.CitizenDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.CommentDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.CoordinatesDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.IssueDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Query.application.interfaces.QueryReportServiceInterface;
import com.devInnovators.Whatchdog.Query.domain.model.AdminC;
import com.devInnovators.Whatchdog.Query.domain.model.Citizen;
import com.devInnovators.Whatchdog.Query.domain.model.Comment;
import com.devInnovators.Whatchdog.Query.domain.model.Coordinates;
import com.devInnovators.Whatchdog.Query.domain.model.Issue;
import com.devInnovators.Whatchdog.Query.domain.model.Report;
import com.devInnovators.Whatchdog.Query.domain.model.Status;
import com.devInnovators.Whatchdog.Query.domain.repository.QueryReportRepository;

import lombok.extern.java.Log;

import org.springframework.data.mongodb.core.query.Query;

@Service
public class QueryReportServiceImpl implements QueryReportServiceInterface {

   
    private final QueryReportRepository reportRepository;

    @Autowired
    public QueryReportServiceImpl(QueryReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
    @Autowired
    private MongoTemplate mongoTemplate;

    public ReportDTO findReportById(String idReport) {
        Report report = reportRepository.findById(idReport)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found with ID: " + idReport));
        return convertReportToDTO(report);
    }
    public List<ReportDTO> findAllReports() {
        List<Report> reports = reportRepository.findAll();
        
        if (reports.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reports found");
            
        }
        return reports.stream()
                      .map(this::convertReportToDTO)
                      .collect(Collectors.toList());
    }
    @Override 
    public List<ReportDTO> findReportsByStatus(Status status) {
        List<Report> reports = reportRepository.findByStatus(status);
        if (reports.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reports found with status: " + status);
        }
        return reports.stream()
                      .map(this::convertReportToDTO)
                      .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByCategoryIssue(String categoryIssue) {
        List<Report> reports = reportRepository.findByCategoryIssue(categoryIssue);
        if (reports.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reports found with category issue: " + categoryIssue);
        }
        return reports.stream()
                      .map(this::convertReportToDTO)
                      .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByAdminId(String adminId) {
        if (adminId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin ID cannot be null");
        }

        // Log para depuración
        System.out.println("Consultando reports con admin ID: " + adminId);
        
        List<Report> reports = reportRepository.findByIdAdminC(adminId);
         // Log para depuración
        

        System.out.println("Número de reports encontrados con MongoTemplate: " + reports.size());

        if (reports.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reports found with admin ID: " + adminId);
        }

        return reports.stream()
                .map(this::convertReportToDTO)
                .collect(Collectors.toList());
    }

    // Método privado para la conversión de Report a ReportDTO
    private ReportDTO convertReportToDTO(Report report) {
        CitizenDTO citizenDTO = convertCitizenToDTO(report.getIdcitizen());
        IssueDTO issueDTO = convertIssueToDTO(report.getIdissue());
        CoordinatesDTO coordinatesDTO = convertCoordinatesToDTO(report.getCoordinates());
        AdminDTO adminDTO = convertAdminToDTO(report.getIdAdminC());
        List<CommentDTO> commentDTOs = convertCommentsToDTOs(report.getComments());

        return new ReportDTO(
            report.getIdReport(),
            report.getDescription(),
            citizenDTO,
            issueDTO,
            report.getStatus(),
            coordinatesDTO,
            report.getCreateDate(),
            report.getUpdateDate(),
            report.getFotoUrl(),
            adminDTO,
            report.getCategoryIssue(),
            commentDTOs, 
            report.getNumLikes(),
            report.getNumDislikes()

        );
    }

    // Métodos privados para convertir entidades anidadas a sus respectivos DTOs
    private CitizenDTO convertCitizenToDTO(Citizen citizen) {
        return new CitizenDTO(
                citizen.getId(),
                citizen.getName(),
                citizen.getEmail(),
                citizen.getPhone()
        );
    }

    private IssueDTO convertIssueToDTO(Issue issue) {
        List<ReportDTO> reportDTOs = issue.getReportsList().stream()
            .map(this::convertReportToDTO) // Asegúrate de tener este método
            .collect(Collectors.toList());

        // Convertir idAdminC a AdminDTO
        AdminDTO adminDTO = convertAdminToDTO(issue.getIdAdminC());

        return new IssueDTO(
                issue.getId(),
                issue.getCategoryIssue(),
                issue.getStatusIssue(),
                issue.getPriority(),
                reportDTOs,
                adminDTO,
                issue.getResolutionTeam()
        );
    }

    private CoordinatesDTO convertCoordinatesToDTO(Coordinates coordinates) {
        return new CoordinatesDTO(
                coordinates.getLatitude(),
                coordinates.getLongitude()
        );
    }

    private AdminDTO convertAdminToDTO(AdminC admin) {
        List<ReportDTO> assignedReportDTOs;

        // Verifica si getAssignedReport() es null
        if (admin.getAssignedReport() != null) {
            assignedReportDTOs = admin.getAssignedReport().stream()
                .map(this::convertReportToDTO) // Asegúrate de que este método existe
                .collect(Collectors.toList());
        } else {
            assignedReportDTOs = new ArrayList<>(); // Crea una lista vacía si es null
        }

        // Crea y devuelve el AdminDTO
        return new AdminDTO(admin.getId(), admin.getName(), assignedReportDTOs);
    }

    private CommentDTO convertCommentToDTO(Comment comment) {
         CitizenDTO citizenDTO = convertCitizenToDTO(comment.getIdcitizen());
         String reportId = comment.getIdreport() != null ? comment.getIdreport().getIdReport() : null; // Obtener ID del Report
        return new CommentDTO(
                comment.getId(),
                comment.getDescription(),
                citizenDTO,
                comment.getCreateDate(),
                reportId
        );
    }
    private List<CommentDTO> convertCommentsToDTOs(List<Comment> comments) {
        return comments.stream()
                .map(this::convertCommentToDTO) // Utiliza el método que convierte un solo Comment a CommentDTO
                .collect(Collectors.toList());
    }
    

}
