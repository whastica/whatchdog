package com.devInnovators.Whatchdog.Query.application.DTO;

import com.devInnovators.Whatchdog.Query.domain.model.StatusIssue;
import com.devInnovators.Whatchdog.Query.domain.model.ResolutionTeam;    

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class IssueDTO {

    private String id;
    private String categoryIssue;
    private StatusIssue statusIssue;  
    private String priority;
    private List<ReportDTO> reportsList; 
    private AdminDTO idAdminC;            
    private ResolutionTeam resolutionTeam; 
}