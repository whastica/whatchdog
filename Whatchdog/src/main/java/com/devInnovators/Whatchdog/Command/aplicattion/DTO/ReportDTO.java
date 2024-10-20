package com.devInnovators.Whatchdog.Command.aplicattion.DTO;

import java.time.LocalDateTime;

import com.devInnovators.Whatchdog.Command.domain.model.Status;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private String id;
    @NotNull
    private String description;
    @NotNull
    private CitizenDTO citizen;
    @NotNull
    private ProblemDTO problem;
    private Status status;
    private CoordinatesDTO coordinates;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String fotoUrl;
}
