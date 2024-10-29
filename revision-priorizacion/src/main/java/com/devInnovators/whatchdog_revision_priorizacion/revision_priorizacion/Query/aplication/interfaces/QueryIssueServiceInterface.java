package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.interfaces;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.DTO.IssueDTO;
import java.util.List;

public interface QueryIssueServiceInterface {

    List<IssueDTO> getAllIssues();
    
    IssueDTO getIssueById(String issueId);
    
    List<IssueDTO> getIssuesByPriority(String priority);
    
    List<IssueDTO> getIssuesByResolutionTeam(String resolutionTeam);
}
