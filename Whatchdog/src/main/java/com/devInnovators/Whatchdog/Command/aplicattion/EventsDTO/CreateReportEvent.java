package com.devInnovators.Whatchdog.Command.aplicattion.EventsDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.devInnovators.Whatchdog.Command.domain.model.CategoryIssue;
import com.devInnovators.Whatchdog.Command.domain.model.Coordinates;
import com.devInnovators.Whatchdog.Command.domain.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReportEvent implements Serializable {
    private String id;                       // ID del reporte creado
    private String description;              // Descripción del reporte
    private String citizenId;                // ID del ciudadano que creó el reporte
    private Status status;                   // Estado inicial del reporte (e.g., NUEVO, EN_PROGRESO)
    private CategoryIssue categoryIssue;     // Categoría del reporte
    private Coordinates coordinates;         // Ubicación del reporte
    private LocalDateTime createDate;        // Fecha de creación del reporte
    private String fotoUrl;    
}
