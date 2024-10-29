package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model;

import java.util.List;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.DTO.IssueDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryIssue {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private CategoryIssue categoryIssue;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @OneToMany(fetch = FetchType.LAZY)
    private List<QueryReport> listReports;

    private String idAdminC;

    @Enumerated(EnumType.STRING)
    private ResolutionTeam resolutionTeam;

    @Enumerated(EnumType.STRING)
    private StatusIssue status;

    // Constructor que acepta IssueDTO
    public QueryIssue(IssueDTO issueDTO) {
        this.id = issueDTO.getId();
        this.categoryIssue = issueDTO.getCategoryIssue();
        this.priority = issueDTO.getPriority();
        this.idAdminC = issueDTO.getIdAdminC();
        this.resolutionTeam = issueDTO.getResolutionTeam();
        // La lista de reportes se debe gestionar manualmente
    }

    // Método para actualizar a partir de IssueDTO
    public void updateFromDTO(IssueDTO issueDTO) {
        this.categoryIssue = issueDTO.getCategoryIssue();
        this.priority = issueDTO.getPriority();
        this.idAdminC = issueDTO.getIdAdminC();
        this.resolutionTeam = issueDTO.getResolutionTeam();
        // La lista de reportes se debe gestionar manualmente
    }

    // Métodos setStatus y setPriority
    public void setStatus(StatusIssue status) {
        this.status = status;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
