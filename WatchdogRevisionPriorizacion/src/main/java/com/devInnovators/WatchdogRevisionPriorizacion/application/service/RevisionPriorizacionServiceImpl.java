package com.devInnovators.WatchdogRevisionPriorizacion.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devInnovators.WatchdogRevisionPriorizacion.application.DTO.IssueDTO;
import com.devInnovators.WatchdogRevisionPriorizacion.application.DTO.ReportDTO;
import com.devInnovators.WatchdogRevisionPriorizacion.application.interfaces.RevisionPriorizacionServiceInterface;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.CategoryIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Issue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Priority;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Report;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.StatusIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.repository.IssueRepository;

import jakarta.transaction.Transactional;

@Service
public class RevisionPriorizacionServiceImpl implements RevisionPriorizacionServiceInterface {

    private final IssueRepository issueRepository;
    private final RestTemplate restTemplate;

    public RevisionPriorizacionServiceImpl(IssueRepository issueRepository, RestTemplate restTemplate) {
        this.issueRepository = issueRepository;
        this.restTemplate = restTemplate;
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

    @Override
    public List<ReportDTO> getReportsByCategoryFromReportService(CategoryIssue category) {
        // URL del endpoint del microservicio de reportes que permite obtener reportes por categoría
        String url = "http://microservicio-reportes/api/v1/reports?category=" + category;
        
        // Llamada al microservicio de reportes y obtención de los reportes por categoría
        ReportDTO[] reports = restTemplate.getForObject(url, ReportDTO[].class);

        return reports != null ? List.of(reports) : List.of();
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