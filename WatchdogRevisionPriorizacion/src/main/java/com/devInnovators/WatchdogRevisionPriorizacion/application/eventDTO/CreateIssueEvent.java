package com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO;


import java.io.Serializable;
import java.util.List;

import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.CategoryIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Priority;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.StatusIssue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIssueEvent implements Serializable {
    private String id;                          
    private CategoryIssue category;             
    private Priority priority;                  
    private StatusIssue statusIssue;            
    private List<String> reportIds;             
    private String admincId; 
}
