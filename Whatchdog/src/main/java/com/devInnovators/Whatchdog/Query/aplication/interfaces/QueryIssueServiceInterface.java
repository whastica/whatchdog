package com.devInnovators.Whatchdog.Query.aplication.interfaces;

import com.devInnovators.Whatchdog.Query.aplication.DTO.IssueDTO;
import java.util.List;

public interface QueryIssueServiceInterface {

    List<IssueDTO> getAllIssues();
    
    IssueDTO getIssueById(String issueId);
    
    List<IssueDTO> getIssuesByPriority(String priority);
    
    List<IssueDTO> getIssuesByResolutionTeam(String resolutionTeam);
}
