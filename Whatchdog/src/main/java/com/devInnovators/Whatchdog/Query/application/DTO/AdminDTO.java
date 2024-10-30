package com.devInnovators.Whatchdog.Query.application.DTO;



import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {

    private String id;
    @NotNull
    private String name;
    private List<ReportDTO> assignedReport;
  

}
