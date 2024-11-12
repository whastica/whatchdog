package com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.CategoryIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Coordinates;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateReportEvent implements Serializable {
    private String id;                      
    private String description;              
    private String admincId;                 
    private Status status;                  
    private CategoryIssue categoryIssue; 
    private String issueId;    
    private Coordinates coordinates; 
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updateDate;        
    private String fotoUrl;
}
