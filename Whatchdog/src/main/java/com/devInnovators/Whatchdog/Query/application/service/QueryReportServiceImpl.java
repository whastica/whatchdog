package com.devInnovators.Whatchdog.Query.application.service;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Query.application.interfaces.QueryReportServiceInterface;
import com.devInnovators.Whatchdog.Query.application.DTO.AdminDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.CitizenDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.CommentDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.IssueDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.CoordinatesDTO;
import com.devInnovators.Whatchdog.Query.domain.model.AdminCQuery;
import com.devInnovators.Whatchdog.Query.domain.model.CitizenQuery;
import com.devInnovators.Whatchdog.Query.domain.model.CommentQuery;
import com.devInnovators.Whatchdog.Query.domain.model.CoordinatesQuery;
import com.devInnovators.Whatchdog.Query.domain.model.IssueQuery;
import com.devInnovators.Whatchdog.Query.domain.model.ReportQuery;
import com.devInnovators.Whatchdog.Query.domain.model.StatusQuery;
import com.devInnovators.Whatchdog.Query.domain.repository.QueryReportRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryReportServiceImpl implements QueryReportServiceInterface {

   
    @Autowired
    private QueryReportRepository queryReportRepository; // Repositorio de consultas

    @Override
    public List<ReportDTO> getAllReports() {
        // Obtener todos los reportes desde la base de datos y convertir a DTOs
        return queryReportRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO getReportById(String id) {
        // Obtener un reporte por ID y convertir a DTO
        return queryReportRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null); // O manejarlo con una excepción
    }

    @Override
    public List<ReportDTO> getReportsByCitizenId(String citizenId) {
        // Obtener reportes por ID de ciudadano
        return queryReportRepository.findByCitizenId(citizenId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByIssueId(String issueId) {
        // Obtener reportes por ID de issue
        return queryReportRepository.findByIssueId(issueId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para convertir ReportQuery a ReportDTO
    private ReportDTO convertToDTO(ReportQuery report) {
        // Lógica para convertir ReportQuery a ReportDTO
        // Deberás convertir también CitizenQuery, IssueQuery, etc.
        return new ReportDTO(
                report.getId(),
                report.getDescription(),
                new CitizenDTO(report.getCitizenId(), null, null, null), // Obtener los datos relevantes de Citizen
                new IssueDTO(report.getIssueId(), null, null, null, null, null), // Obtener datos relevantes de Issue
                report.getStatus(),
                report.getCategoryIssue(),
                report.getCoordinates(),
                report.getCreateDate(),
                report.getUpdateDate(),
                report.getFotoUrl(),
                new AdminDTO(report.getAdminCId(), null, null), // Obtener datos relevantes de Admin
                null, // Cambia esto por la conversión real de comments
                report.getNumLikes(),
                report.getNumDislikes()
        );
    }
}
