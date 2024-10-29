package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.DTO;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model.Comment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String id;
    private String description;
    private String idCitizen;
    private LocalDateTime date;
    private String reportId;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.idCitizen = comment.getIdCitizen();
        this.date = comment.getDate();
        this.reportId = comment.getReport() != null ? comment.getReport().getId() : null;
    }
}
