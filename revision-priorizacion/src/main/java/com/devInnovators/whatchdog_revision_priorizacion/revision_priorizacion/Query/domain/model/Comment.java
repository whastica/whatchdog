package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model;

import java.time.LocalDateTime;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.DTO.CommentDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    private String id;

    private String description;
    private String idCitizen;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private QueryReport report;

    public Comment(CommentDTO commentDTO) {
        this.id = commentDTO.getId();
        this.description = commentDTO.getDescription();
        this.idCitizen = commentDTO.getIdCitizen();
        this.date = commentDTO.getDate();
    }
}
