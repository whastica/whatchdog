package com.devInnovators.Whatchdog.Query.application.DTO;

import com.devInnovators.Whatchdog.Query.domain.model.QueryStatusIssue;
import com.devInnovators.Whatchdog.Query.domain.model.QueryCategoryIssue;
import com.devInnovators.Whatchdog.Query.domain.model.QueryResolutionTeam;   
import com.devInnovators.Whatchdog.Query.domain.model.QueryPriority; 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class IssueDTO {

    private String id;
    private QueryCategoryIssue categoryIssue;
    private QueryPriority priority;
    private QueryStatusIssue statusIssue;  
    private QueryResolutionTeam resolutionTeam; 
    private List<String> reportsList; 
    private AdminDTO idAdminC;            
    
}