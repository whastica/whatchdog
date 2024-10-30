package com.devInnovators.Whatchdog.Query.application.DTO;

import java.time.LocalDateTime;



import com.devInnovators.Whatchdog.Query.domain.model.Status;
import com.devInnovators.Whatchdog.Query.domain.model.CategoryIssue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private String id;
    private String description;
    private CitizenDTO idcitizen;       // Usar CitizenDTO para representar el ciudadano
    private IssueDTO idissue;           // Usar IssueDTO para representar el issue
    private Status status;           // Usar StatusDTO para representar el estado
    private CoordinatesDTO coordinates; // Usar CoordinatesDTO para representar las coordenadas
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String fotoUrl;
    private AdminDTO idAdminC;        // Usar AdminCDTO para representar el administrador
    private CategoryIssue categoryIssue; // Usar CategoryIssueDTO para representar la categoría
    private List<CommentDTO> comments;  // Lista de CommentDTO para representar los comentarios
    private Long numLikes;
    private Long numDislikes;
}

