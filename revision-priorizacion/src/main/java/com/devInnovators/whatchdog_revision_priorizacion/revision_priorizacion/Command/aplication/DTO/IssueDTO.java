package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.aplication.DTO;

import java.util.List;
import java.util.stream.Collectors;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.CategoryIssue;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.Issue;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.Priority;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.ResolutionTeam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {
    private String id;
    private CategoryIssue categoryIssue;
    private Priority priority;
    private List<ReportDTO> listReports;
    private String idAdminC;
    private ResolutionTeam resolutionTeam;

     public IssueDTO(Issue issue) {
        this.id = issue.getId();
        this.categoryIssue = issue.getCategoryIssue();
        this.priority = issue.getPriority();
        this.idAdminC = issue.getIdAdminC();
        this.resolutionTeam = issue.getResolutionTeam();
        this.listReports = issue.getListReports().stream()
            .map(ReportDTO::new)
            .collect(Collectors.toList());
    }
}
