package com.devInnovators.Whatchdog.Query.application.service;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Query.application.interfaces.QueryReportServiceInterface;
/* import com.devInnovators.Whatchdog.Command.domain.model.Report;
import com.devInnovators.Whatchdog.Query.application.DTO.AdminDTO; */
import com.devInnovators.Whatchdog.Query.application.DTO.CitizenDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.CommentDTO;
import com.devInnovators.Whatchdog.Query.domain.model.QueryAdminC;
/* import com.devInnovators.Whatchdog.Query.application.DTO.IssueDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.CoordinatesDTO;
import com.devInnovators.Whatchdog.Query.domain.model.QueryAdminC; */
import com.devInnovators.Whatchdog.Query.domain.model.QueryCitizen;
import com.devInnovators.Whatchdog.Query.domain.model.QueryComment;
import com.devInnovators.Whatchdog.Query.domain.model.QueryIssue;
/* import com.devInnovators.Whatchdog.Query.domain.model.QueryCoordinates;
import com.devInnovators.Whatchdog.Query.domain.model.QueryIssue; */
import com.devInnovators.Whatchdog.Query.domain.model.QueryReport;
import com.devInnovators.Whatchdog.Query.domain.model.QueryStatus;
import com.devInnovators.Whatchdog.Query.domain.repository.QueryReportRepository;
import com.devInnovators.Whatchdog.Query.domain.repository.QueryCitizenRepository;
import com.devInnovators.Whatchdog.Query.domain.repository.QueryIssueRepository;
import com.devInnovators.Whatchdog.Query.domain.repository.QueryAdminCRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryReportServiceImpl implements QueryReportServiceInterface {

   
    private final QueryReportRepository reportRepository;
    private final QueryCitizenRepository citizenRepository;
    private final QueryIssueRepository issueRepository;
    private final QueryAdminCRepository adminCRepository;


    @Autowired
    public QueryReportServiceImpl(QueryReportRepository reportRepository, QueryCitizenRepository citizenRepository, QueryIssueRepository issueRepository, QueryAdminCRepository adminCRepository) {
        this.reportRepository = reportRepository;
        this.citizenRepository = citizenRepository;
        this.issueRepository = issueRepository;
        this.adminCRepository = adminCRepository;
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

   /*  // Métodos privados para convertir entidades anidadas a sus respectivos DTOs
    private CitizenDTO convertCitizenToDTO(QueryCitizen citizen) {
        return new CitizenDTO(
                citizen.getId(),
                citizen.getName(),
                citizen.getEmail(),
                citizen.getPhone()
        );
    }
 */
   /*  private IssueDTO convertIssueToDTO(QueryIssue issue) {
       // Convertir cada reporte en una lista de IDs de reporte (String)
        List<String> reportIds = issue.getReportsList().stream()
            .map(QueryReport::getIdReport)  // Extrae el ID de cada reporte
            .collect(Collectors.toList());

        // Convertir idAdminC a AdminDTO
        AdminDTO adminDTO = convertAdminToDTO(issue.getIdAdminC());

        return new IssueDTO(
            issue.getId(),
            issue.getCategoryIssue(),
            issue.getPriority(),
            issue.getStatusIssue(),
            issue.getResolutionTeam(),
            reportIds,       // Lista de IDs de los reportes
            adminDTO
        );
    } */
/* 
    private CoordinatesDTO convertCoordinatesToDTO(QueryCoordinates coordinates) {
        return new CoordinatesDTO(
                coordinates.getLatitude(),
                coordinates.getLongitude()
        );
    }

    private AdminDTO convertAdminToDTO(QueryAdminC admin) {
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
    } */

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
