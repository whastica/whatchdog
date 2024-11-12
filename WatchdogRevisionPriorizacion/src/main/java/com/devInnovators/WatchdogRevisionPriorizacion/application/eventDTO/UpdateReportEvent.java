package com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO;

import java.time.LocalDateTime;

import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.CategoryIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Coordinates;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateReportEvent {
    private String id;                      
    private String description;              
    private String admincId;                 
    private Status status;                  
    private CategoryIssue categoryIssue; 
    private String issueId;    
    private Coordinates coordinates;        
    private LocalDateTime updateDate;        
    private String fotoUrl;
}
