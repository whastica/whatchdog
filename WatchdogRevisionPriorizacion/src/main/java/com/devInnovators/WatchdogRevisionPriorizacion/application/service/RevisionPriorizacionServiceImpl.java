package com.devInnovators.WatchdogRevisionPriorizacion.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devInnovators.WatchdogRevisionPriorizacion.application.DTO.IssueDTO;
import com.devInnovators.WatchdogRevisionPriorizacion.application.DTO.ReportDTO;
import com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO.UpdateReportEvent;
import com.devInnovators.WatchdogRevisionPriorizacion.application.interfaces.RevisionPriorizacionServiceInterface;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Issue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Priority;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Report;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.StatusIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.repository.IssueRepository;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.repository.ReportRepository;
import com.devInnovators.WatchdogRevisionPriorizacion.infra.publisher.EventPublisher;

import jakarta.transaction.Transactional;

@Service
public class RevisionPriorizacionServiceImpl implements RevisionPriorizacionServiceInterface {

    private final IssueRepository issueRepository;
    private final ReportRepository reportRepository;
    private EventPublisher eventPublisher;

    public RevisionPriorizacionServiceImpl(IssueRepository issueRepository, ReportRepository reportRepository) {
        this.issueRepository = issueRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    @Transactional
    public IssueDTO createIssue(IssueDTO issueDTO) {
        Issue issue = new Issue();
        issue.setCategoryIssue(issueDTO.getCategory());
        issue.setCategoryIssue(issueDTO.getCategory());
        issue.setStatusIssue(StatusIssue.ASIGNADO); // Estado inicial
        issue.setPriority(issueDTO.getPriority());

        Issue savedIssue = issueRepository.save(issue);
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
                    ? issue.getReportList().stream().map(Report::getId).collect(Collectors.toList()) 
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
    public void processUpdateReport(UpdateReportEvent event) {
        // Obtener el reporte usando su ID
        Report report = reportRepository.findById(event.getId())
            .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado con ID: " + event.getId()));

        // Actualizar los campos del reporte con los valores del evento
        report.setDescription(event.getDescription());
        report.setStatus(event.getStatus()); // Actualizar el estado
        report.setCategoryIssue(event.getCategoryIssue()); // Actualizar la categoría
        report.setCoordinates(event.getCoordinates()); // Actualizar las coordenadas
        report.setUpdateDate(event.getUpdateDate()); // Actualizar la fecha de actualización
        report.setFotoUrl(event.getFotoUrl()); // Actualizar la URL de la foto

        // Si el evento tiene un nuevo 'issueId', actualizar también la relación con el Issue
        if (event.getIssueId() != null) {
            Issue issue = issueRepository.findById(event.getIssueId())
                .orElseThrow(() -> new IllegalArgumentException("Issue no encontrado con ID: " + event.getIssueId()));
            report.setIssue(issue); // Establecer la relación con el issue
        }

        // Guardar el reporte actualizado
        reportRepository.save(report);

        // 5. Disparar el evento de actualización
        UpdateReportEvent updatedReportEvent = new UpdateReportEvent(
            report.getId(),
            report.getDescription(),
            report.getAdminC() != null ? report.getAdminC().getId() : null,  // Aquí agregamos admincId
            report.getStatus(),
            report.getCategoryIssue(),
            report.getIssue() != null ? report.getIssue().getId() : null,
            report.getCoordinates(),
            report.getUpdateDate(),
            report.getFotoUrl()
        );

        // Publicar el evento
        eventPublisher.publishUpdateReportEvent(updatedReportEvent);
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
        // Mapear otros campos si es necesario
        return dto;
    }
}