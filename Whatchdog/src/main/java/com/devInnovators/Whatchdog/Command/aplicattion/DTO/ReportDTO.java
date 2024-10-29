package com.devInnovators.Whatchdog.Command.aplicattion.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.devInnovators.Whatchdog.Command.domain.model.CategoryIssue;
import com.devInnovators.Whatchdog.Command.domain.model.Coordinates;
import com.devInnovators.Whatchdog.Command.domain.model.Status;

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
