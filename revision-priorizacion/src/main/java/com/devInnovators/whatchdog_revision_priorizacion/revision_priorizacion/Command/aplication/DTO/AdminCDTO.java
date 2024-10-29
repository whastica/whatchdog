package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.aplication.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCDTO {
    private String id;
    private String name;
    private List<ReportDTO> assignedReports;
}
