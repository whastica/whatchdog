package com.devInnovators.Whatchdog.Command.domain.model;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)  // Asociación con Citizen
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    
    @ManyToOne(fetch = FetchType.LAZY)  // Asociación con report
    @JoinColumn(name = "report_id")
    private Report report;

    private LocalDateTime createDate;
}
