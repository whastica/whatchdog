package com.devInnovators.WatchdogRevisionPriorizacion.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Data                   // Genera los getters, setters, toString, equals, y hashCode.
@AllArgsConstructor      // Genera un constructor con todos los campos.
@NoArgsConstructor
public class Report {

    @Id
    @Column(name = "_id")
    private String _id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)  // Asociación con Citizen
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    @ManyToOne(fetch = FetchType.LAZY)  // Asociación con Issue
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)  // Asociación con adminc 
    @JoinColumn(name = "adminc_id")
    private AdminC adminC;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @Enumerated(EnumType.STRING)  // Mapeo del enum Status como String en la BD
    private Status status;

    @Enumerated(EnumType.STRING)  // Mapeo del enum Status como String en la BD
    private CategoryIssue categoryIssue;

    @Embedded   // Mapeo de las coordenadas como un objeto embebido
    private Coordinates coordinates;
    
    private LocalDateTime createDate;
    
    private LocalDateTime updateDate;

    private String fotoUrl;

    private Long numLikes;

    private Long numDislikes;
    
    
    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();   // Inicialización automática en creación
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDateTime.now();   // Actualización automática al modificar
    }

    /*public Object getProblem() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProblem'");
    }*/
}
