package com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.CategoryIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevisedReportEvent implements Serializable {
    private String _id;                       
    private String admincId;                 
    private Status status;                   
    private CategoryIssue categoryIssue;   
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  
    private LocalDateTime updateDate; 
    private String issueId;       
}
