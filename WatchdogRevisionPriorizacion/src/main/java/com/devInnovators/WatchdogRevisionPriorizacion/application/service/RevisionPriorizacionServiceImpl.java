package com.devInnovators.WatchdogRevisionPriorizacion.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devInnovators.WatchdogRevisionPriorizacion.application.DTO.IssueDTO;
import com.devInnovators.WatchdogRevisionPriorizacion.application.DTO.ReportDTO;
import com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO.CreateIssueEvent;
import com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO.RevisedReportEvent;

import com.devInnovators.WatchdogRevisionPriorizacion.application.interfaces.RevisionPriorizacionServiceInterface;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.AdminC;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Issue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Priority;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Report;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.StatusIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.repository.IssueRepository;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.repository.ReportRepository;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.repository.AdminRepository;
import com.devInnovators.WatchdogRevisionPriorizacion.infra.publisher.EventPublisher;

import jakarta.transaction.Transactional;

@Service
public class RevisionPriorizacionServiceImpl implements RevisionPriorizacionServiceInterface {

    private final IssueRepository issueRepository;
    private final ReportRepository reportRepository;
    private final AdminRepository adminRepository;
    private EventPublisher eventPublisher;

    public RevisionPriorizacionServiceImpl(IssueRepository issueRepository, ReportRepository reportRepository,
     AdminRepository adminRepository, EventPublisher eventPublisher) {
        this.issueRepository = issueRepository;
        this.reportRepository = reportRepository;
        this.adminRepository = adminRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public IssueDTO createIssue(IssueDTO issueDTO) {
        Issue issue = new Issue();
        issue.setId(issueDTO.getId());
        issue.setCategoryIssue(issueDTO.getCategory());
        issue.setStatusIssue(StatusIssue.ASIGNADO); // Estado inicial
        issue.setPriority(issueDTO.getPriority());

        // Mapeo de reportIds a reportList
        if (issueDTO.getReportIds() != null && !issueDTO.getReportIds().isEmpty()) {
            List<Report> reports = issueDTO.getReportIds().stream()
                    .map(reportId -> {
                        Report report = reportRepository.findById(reportId)
                                .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado: " + reportId));
                        report.setIssue(issue); // Establece la relación bidireccional
                        return report;
                    })
                    .collect(Collectors.toList());
            issue.setReportList(reports);
        }
            // Mapeo de admincId a AdminC
        if (issueDTO.getAdmincId() != null) {
            AdminC adminc = adminRepository.findById(issueDTO.getAdmincId())
                    .orElseThrow(() -> new IllegalArgumentException("AdminC no encontrado: " + issueDTO.getAdmincId()));
            issue.setAdminc(adminc);
        }
        Issue savedIssue = issueRepository.save(issue);
        System.out.println("Antes de publicar el evento...");
           // Publicar el evento a RabbitMQ
         // Construir el evento CreateIssueEvent
        CreateIssueEvent event = new CreateIssueEvent(
            savedIssue.getId(),
            savedIssue.getCategoryIssue(),
            savedIssue.getPriority(),
            savedIssue.getStatusIssue(),
            savedIssue.getReportList() != null ? savedIssue.getReportList().stream()
                .map(Report::get_id)
                .collect(Collectors.toList()) : null,
            savedIssue.getAdminc() != null ? savedIssue.getAdminc().getId() : null
        );
        eventPublisher.publishCreateIssueEvent(event);
        System.out.println("Después de publicar el evento...");
        return convertToDTO(savedIssue);
    }

    @Override
    @Transactional
    public IssueDTO updateIssueStatus(String issueId, StatusIssue statusIssue) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Issue not found"));

        issue.setStatusIssue(statusIssue);
        issueRepository.save(issue);
        return convertToDTO(issue);
    }

    @Override
    public List<IssueDTO> getAllIssues() {
        return issueRepository.findAll().stream()
            .map(issue -> new IssueDTO(
                issue.getId(),
                issue.getCategoryIssue(),
                issue.getPriority(),
                issue.getStatusIssue(),
                issue.getResolutionTeam(),
                issue.getReportList() != null 
                    ? issue.getReportList().stream().map(Report::get_id).collect(Collectors.toList()) 
                    : new ArrayList<>(),
                issue.getAdminc() != null ? issue.getAdminc().getId() : null
            ))
            .collect(Collectors.toList());
    }

    /*@Override
    public List<ReportDTO> getReportsByCategoryFromReportService(CategoryIssue category) {
        // URL del endpoint del microservicio de reportes que permite obtener reportes por categoría
        String url = "http://microservicio-reportes/api/v1/reports?category=" + category;
        
        // Llamada al microservicio de reportes y obtención de los reportes por categoría
        ReportDTO[] reports = restTemplate.getForObject(url, ReportDTO[].class);

        return reports != null ? List.of(reports) : List.of();
    }*/

    @Override
    public void RevisedReport(RevisedReportEvent event) {
        // Obtener el reporte usando su ID
    Report report = reportRepository.findById(event.get_id())
    .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado con ID: " + event.get_id()));

    // Actualizar los campos del reporte con los valores del evento
        report.setAdminC(adminRepository.findById(event.getAdmincId())
            .orElseThrow(() -> new IllegalArgumentException("AdminC no encontrado con ID: " + event.getAdmincId())));
        report.setStatus(event.getStatus()); // Actualizar el estado
        report.setCategoryIssue(event.getCategoryIssue()); // Actualizar la categoría
        report.setUpdateDate(event.getUpdateDate()); // Actualizar la fecha de actualización

        // Guardar el reporte actualizado
        reportRepository.save(report);

        // 5. Disparar el evento de actualización
        RevisedReportEvent eventRevised = new RevisedReportEvent(
            report.get_id(),
            report.getAdminC().getId(), // Asegurar que adminC no sea nulo
            report.getStatus(),
            report.getCategoryIssue(),
            report.getUpdateDate(),
            report.getIssue() != null ? report.getIssue().getId() : null // Validar que issue no sea nulo
        );

        // Publicar el evento
        eventPublisher.publishRevisedReportEvent(eventRevised); 
    }
    @Override
    public List<ReportDTO> prioritizeReports(List<ReportDTO> reports, Priority priority) {
        // Lógica para ordenar los reportes de acuerdo con el criterio de prioridad
        return null;
    }

    private IssueDTO convertToDTO(Issue issue) {
        IssueDTO dto = new IssueDTO();
        dto.setId(issue.getId());
        dto.setCategory(issue.getCategoryIssue());
        dto.setPriority(issue.getPriority());
        dto.setStatusIssue(issue.getStatusIssue());
        dto.setResolutionTeam(issue.getResolutionTeam());
          // Convertir la lista de reportes a una lista de IDs
          if (issue.getReportList() != null) {
            dto.setReportIds(issue.getReportList().stream()
                    .map(Report::get_id)
                    .collect(Collectors.toList()));
        }

        // Establecer admincId
        if (issue.getAdminc() != null) {
            dto.setAdmincId(issue.getAdminc().getId());
        }
        // Mapear otros campos si es necesario
        return dto;
    }
}