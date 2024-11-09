package com.devInnovators.Whatchdog.Query.application.DTO;

import java.time.LocalDateTime;



import com.devInnovators.Whatchdog.Query.domain.model.QueryStatus;
import com.devInnovators.Whatchdog.Query.domain.model.QueryCategoryIssue;
import com.devInnovators.Whatchdog.Query.domain.model.QueryCoordinates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private String idReport;
    private String description;
    private String idcitizen;       // Usar CitizenDTO para representar el ciudadano
    private String idissue; 
    private String idAdminC;  
    private List<CommentDTO> comments;         // Usar IssueDTO para representar el issue
    private QueryStatus status; 
    private String categoryIssue;          // Usar StatusDTO para representar el estado
    private QueryCoordinates coordinates; // Usar CoordinatesDTO para representar las coordenadas
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String fotoUrl;
    private Long numLikes;
    private Long numDislikes;
}

