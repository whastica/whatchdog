package com.devInnovators.Whatchdog.Query.domain.model;


import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "issue")        
public class Issue {
    @Id
    private String id;
   
    private String categoryIssue;

    private StatusIssue statusIssue;
    private String priority;
    @DBRef(lazy = true) 
    private List<Report> reportsList;
    @DBRef(lazy = true) 
    private AdminC adminC;
   
    private ResolutionTeam resolutionTeam;
   

}

