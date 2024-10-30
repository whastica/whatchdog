package com.devInnovators.Whatchdog.Query.application.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private String id;
    private String description;
    private CitizenDTO idcitizen;  // Usar CitizenDTO para representar el ciudadano
    private LocalDateTime createDate;
    private String idreport;     // Usar ReportDTO para representar el reporte
}