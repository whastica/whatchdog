package com.devInnovators.Whatchdog.Command.domain.model;

import java.time.LocalDateTime;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.CitizenDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.DTO.CoordinatesDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ProblemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   // Genera los getters, setters, toString, equals, y hashCode.
@AllArgsConstructor      // Genera un constructor con todos los campos.
@NoArgsConstructor 
public class Report {

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
