package com.devInnovators.WatchdogRevisionPriorizacion.application.interfaces;

import java.util.List;

import com.devInnovators.WatchdogRevisionPriorizacion.application.DTO.IssueDTO;
import com.devInnovators.WatchdogRevisionPriorizacion.application.DTO.ReportDTO;
import com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO.RevisedReportEvent;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Priority;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.StatusIssue;

public interface RevisionPriorizacionServiceInterface {
    // Métodos para manejar Issues
    IssueDTO createIssue(IssueDTO issueDTO);
    IssueDTO updateIssueStatus(String issueId, StatusIssue statusIssue);
    List<IssueDTO> getAllIssues();

    // Métodos para manejar la priorización de reportes por categoría
    //List<ReportDTO> getReportsByCategoryFromReportService(CategoryIssue category);

    // Método para obtener reportes priorizados (llamada al servicio de reportes y organización local)
    List<ReportDTO> prioritizeReports(List<ReportDTO> reports, Priority priority);

    void RevisedReport(RevisedReportEvent event);

}
