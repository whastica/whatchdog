package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.DTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model.CategoryIssue;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model.Coordinates;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model.QueryReport;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private String id;
    private String description;
    private Status status;
    private Coordinates coordinates;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String fotoUrl;
    private String idAdmin;
    private String idCitizen;
    private String issueId;
    private CategoryIssue categoryIssue;
    private List<CommentDTO> listComments;
    private int numLikes;
    private int numDislikes;

    // Constructor que acepta un objeto Report
    public ReportDTO(QueryReport report) {
        this.id = report.getId();
        this.description = report.getDescription();
        this.status = report.getStatus(); 
        this.coordinates = report.getCoordinates();  
        this.createDate = report.getCreateDate();
        this.updateDate = report.getUpdateDate();
        this.fotoUrl = report.getFotoUrl();
        this.idAdmin = report.getIdAdmin();
        this.idCitizen = report.getIdCitizen();
        this.issueId = report.getIssue() != null ? report.getIssue().getId() : null;
        this.categoryIssue = report.getCategoryIssue();  
        this.listComments = report.getListComments().stream()
            .map(CommentDTO::new)
            .collect(Collectors.toList());
        this.numLikes = report.getNumLikes();
        this.numDislikes = report.getNumDislikes();
    }
}
