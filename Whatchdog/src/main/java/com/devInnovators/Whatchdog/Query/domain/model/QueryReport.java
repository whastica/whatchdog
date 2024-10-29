package com.devInnovators.Whatchdog.Query.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import com.devInnovators.Whatchdog.Query.aplication.DTO.ReportDTO;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryReport {

    @Id
    private String id;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Embedded
    private Coordinates coordinates;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private String fotoUrl;
    private String idAdmin;
    private String idCitizen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private QueryIssue issue;

    @Enumerated(EnumType.STRING)
    private CategoryIssue categoryIssue;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> listComments;

    private int numLikes;
    private int numDislikes;

    // Constructor que acepta ReportDTO
    public QueryReport(ReportDTO reportDTO) {
        this.id = reportDTO.getId();
        this.description = reportDTO.getDescription();
        this.status = reportDTO.getStatus();
        this.coordinates = reportDTO.getCoordinates();
        this.fotoUrl = reportDTO.getFotoUrl();
        this.idAdmin = reportDTO.getIdAdmin();
        this.idCitizen = reportDTO.getIdCitizen();
        this.categoryIssue = reportDTO.getCategoryIssue();
        this.numLikes = reportDTO.getNumLikes();
        this.numDislikes = reportDTO.getNumDislikes();
        // La creación y actualización de fechas se maneja automáticamente
    }

    // Método para actualizar a partir de ReportDTO
    public void updateFromDTO(ReportDTO reportDTO) {
        this.description = reportDTO.getDescription();
        this.status = reportDTO.getStatus();
        this.coordinates = reportDTO.getCoordinates();
        this.fotoUrl = reportDTO.getFotoUrl();
        this.idAdmin = reportDTO.getIdAdmin();
        this.idCitizen = reportDTO.getIdCitizen();
        this.categoryIssue = reportDTO.getCategoryIssue();
        this.numLikes = reportDTO.getNumLikes();
        this.numDislikes = reportDTO.getNumDislikes();
        // La actualización de fecha se maneja en el método `onUpdate`
    }

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDateTime.now();
    }
}
