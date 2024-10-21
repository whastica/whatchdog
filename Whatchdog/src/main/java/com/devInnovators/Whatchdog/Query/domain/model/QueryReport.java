package com.devInnovators.Whatchdog.Query.domain.model;

import java.time.LocalDateTime;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.CitizenDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.DTO.CoordinatesDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ProblemDTO;
import com.devInnovators.Whatchdog.Command.domain.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryReport {

    private String id;
    private String description;
    private CitizenDTO citizen;
    private ProblemDTO problem;
    private Status status;
    private CoordinatesDTO coordinates;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String fotoUrl;

}
