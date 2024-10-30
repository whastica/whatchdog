package com.DevInnovators.WatchdogNotificacion.application.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.DevInnovators.WatchdogNotificacion.domain.model.CategoryIssue;
import com.DevInnovators.WatchdogNotificacion.domain.model.Coordinates;

import ch.qos.logback.core.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private String id;
    private String description;
    private String citizenId;                // ID de Citizen
    private String issueId;                  // ID de Issue
    private String admincId;                 // ID de AdminC
    private List<String> commentIds;         // Lista de IDs de los comentarios
    private Status status;
    private CategoryIssue categoryIssue;
    private Coordinates coordinates;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String fotoUrl;
    private Long numLikes;
    private Long numDislikes;
}