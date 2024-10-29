package com.devInnovators.Whatchdog.Command.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data                   // Genera los getters, setters, toString, equals, y hashCode.
@AllArgsConstructor      // Genera un constructor con todos los campos.
@NoArgsConstructor
public class Report {

    @Id
    private String id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)  // Asociación con Citizen
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    @ManyToOne(fetch = FetchType.LAZY)  // Asociación con Issue
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @Enumerated(EnumType.STRING)  // Mapeo del enum Status como String en la BD
    private Status status;

    @Embedded   // Mapeo de las coordenadas como un objeto embebido
    private Coordinates coordinates;

    private LocalDateTime createDate;
    
    private LocalDateTime updateDate;

    private String fotoUrl;

    private String idAdmin;

    private String idIssue;

    private String idCitizen;

    private CategoryIssue categoryIssue;

    private Long numLikes;

    private Long numDislikes;

    private List<Comment> comments;


    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();   // Inicialización automática en creación
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDateTime.now();   // Actualización automática al modificar
    }
}
