package com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO;

import java.time.LocalDateTime;

import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.CategoryIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Issue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevisedReportEvent {
    private String id;                       
    private String admincId;                 
    private Status status;                   
    private CategoryIssue categoryIssue;     
    private LocalDateTime updateDate; 
    private Issue issueId;       
}
