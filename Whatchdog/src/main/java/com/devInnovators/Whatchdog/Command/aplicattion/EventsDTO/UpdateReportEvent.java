package com.devInnovators.Whatchdog.Command.aplicattion.EventsDTO;

import java.io.Serializable;
import java.time.LocalDateTime;


import com.devInnovators.Whatchdog.Command.domain.model.CategoryIssue;
import com.devInnovators.Whatchdog.Command.domain.model.Coordinates;
import com.devInnovators.Whatchdog.Command.domain.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import com.fasterxml.jackson.annotation.JsonFormat;
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

