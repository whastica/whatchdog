package com.devInnovators.Whatchdog.Command.aplicattion.DTO;


import java.util.List;

import com.devInnovators.Whatchdog.Command.domain.model.Priority;
import com.devInnovators.Whatchdog.Command.domain.model.ResolutionTeam;
import com.devInnovators.Whatchdog.Command.domain.model.StatusIssue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {
    
    private String id;
    private String category;
    private Priority priority;
    private StatusIssue statusIssue;
    private ResolutionTeam resolutionTeam;
    private List<String> reportIds;  // Lista de IDs de los reportes asociados
    private String admincId;
}
