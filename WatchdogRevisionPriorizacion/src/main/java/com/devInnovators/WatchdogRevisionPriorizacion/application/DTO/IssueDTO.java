package com.devInnovators.WatchdogRevisionPriorizacion.application.DTO;

import java.util.List;

import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.CategoryIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Priority;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.ResolutionTeam;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.StatusIssue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {
    
    private String id;
    private CategoryIssue category;
    private Priority priority;
    private StatusIssue statusIssue;
    private ResolutionTeam resolutionTeam;
    private List<String> reportIds;  // Lista de IDs de los reportes asociados
    private String admincId;
}
