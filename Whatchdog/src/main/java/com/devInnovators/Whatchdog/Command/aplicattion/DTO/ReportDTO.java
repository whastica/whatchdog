package com.devInnovators.Whatchdog.Command.aplicattion.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.devInnovators.Whatchdog.Command.domain.model.CategoryIssue;
import com.devInnovators.Whatchdog.Command.domain.model.Coordinates;
import com.devInnovators.Whatchdog.Command.domain.model.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private String _id;
    private String description;
    private String idCitizen;                // ID de Citizen
    private String idissue;                  // ID de Issue
    private String idAdminC;  
    private List<CommentDTO> comment = new ArrayList<>();           // Lista de IDs de los comentarios
    private Status status;
    private CategoryIssue categoryIssue;
    private Coordinates coordinates;
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updateDate;
    private String fotoUrl;
    private Long numLikes;
    private Long numDislikes;
}
