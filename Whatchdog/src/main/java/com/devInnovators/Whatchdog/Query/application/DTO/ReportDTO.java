package com.devInnovators.Whatchdog.Query.application.DTO;

import java.time.LocalDateTime;


import com.devInnovators.Whatchdog.Query.domain.model.Status;

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
    private IssueDTO issue;
    private Status status;
    private CoordinatesDTO coordinates;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String fotoUrl;

}
