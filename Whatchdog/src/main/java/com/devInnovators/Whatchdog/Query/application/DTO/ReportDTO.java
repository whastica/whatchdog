package com.devInnovators.Whatchdog.Query.application.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.devInnovators.Whatchdog.Query.domain.model.CategoryIssueQuery;
import com.devInnovators.Whatchdog.Query.domain.model.CoordinatesQuery;
import com.devInnovators.Whatchdog.Query.domain.model.StatusQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private String id;
    private String description;
    private CitizenDTO idcitizen; 
    private IssueDTO idissue;          
    private StatusQuery status;          
    private CoordinatesDTO coordinates; 
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String fotoUrl;
    private AdminDTO idAdminC;       
    private CategoryIssueQuery categoryIssue; 
    private List<CommentDTO> comments;  
    private Long numLikes;
    private Long numDislikes;

    // Asegúrate de que este constructor exista
    public ReportDTO(String id, String description, CitizenDTO citizen, IssueDTO issue,
                     StatusQuery status, CategoryIssueQuery categoryIssue, 
                     CoordinatesQuery coordinates, LocalDateTime createDate,
                     LocalDateTime updateDate, String fotoUrl, AdminDTO admin,
                     List<CommentDTO> comments, Long numLikes, Long numDislikes) {
        this.id = id;
        this.description = description;
        this.citizen = citizen;
        this.issue = issue;
        this.status = status;
        this.categoryIssue = categoryIssue;
        this.coordinates = coordinates;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.fotoUrl = fotoUrl;
        this.admin = admin;
        this.comments = comments;
        this.numLikes = numLikes;
        this.numDislikes = numDislikes;
    }
}

