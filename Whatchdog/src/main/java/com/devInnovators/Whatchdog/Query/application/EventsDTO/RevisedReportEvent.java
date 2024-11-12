package com.devInnovators.Whatchdog.Query.application.EventsDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.devInnovators.Whatchdog.Query.domain.model.QueryStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.devInnovators.Whatchdog.Query.domain.model.QueryCategoryIssue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevisedReportEvent implements Serializable {
    private String id;                       
    private String admincId;                 
    private QueryStatus status;                   
    private QueryCategoryIssue categoryIssue;    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") 
    private LocalDateTime updateDate; 
    private String issueId;
}