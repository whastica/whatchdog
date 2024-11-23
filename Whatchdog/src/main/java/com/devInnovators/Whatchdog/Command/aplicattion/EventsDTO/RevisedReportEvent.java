package com.devInnovators.Whatchdog.Command.aplicattion.EventsDTO;


import java.time.LocalDateTime;

import com.devInnovators.Whatchdog.Command.domain.model.CategoryIssue;
import com.devInnovators.Whatchdog.Command.domain.model.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

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