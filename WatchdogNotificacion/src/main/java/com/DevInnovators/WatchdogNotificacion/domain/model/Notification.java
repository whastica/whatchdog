package com.DevInnovators.WatchdogNotificacion.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data                   // Genera los getters, setters, toString, equals, y hashCode.
@AllArgsConstructor      // Genera un constructor con todos los campos.
@NoArgsConstructor
public class Notification {

    @Id
    private String id;
    private String description;
    
    private LocalDateTime createDate;
    private String idAdmin;
    private String idIssue;
    private String idReport;
    private String idCitizen;

}
