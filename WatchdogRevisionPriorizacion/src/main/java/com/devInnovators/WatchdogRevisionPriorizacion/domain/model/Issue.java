package com.devInnovators.WatchdogRevisionPriorizacion.domain.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private CategoryIssue categoryIssue;

    @Enumerated(EnumType.STRING)
    private StatusIssue statusIssue;

    @Enumerated(EnumType.STRING)  // Guarda el enum como una cadena en la base de datos
    private Priority priority;


    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Report> reportList;

    @ManyToOne(fetch = FetchType.LAZY)  // Asociación con adminc 
    @JoinColumn(name = "adminc_id")
    private AdminC adminc;

    @Enumerated(EnumType.STRING)
    private ResolutionTeam resolutionTeam;

}