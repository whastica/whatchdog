package com.devInnovators.Whatchdog.Query.domain.model;


import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "issue")        
public class QueryIssue {
    @Id
    private String id;
   
    private QueryCategoryIssue categoryIssue;

    private QueryStatusIssue statusIssue;
    private QueryPriority priority;
    @DBRef(lazy = true) 
    private List<QueryReport> reportsList;
    @DBRef(lazy = true) 
    private QueryAdminC idAdminC;
   
    private QueryResolutionTeam resolutionTeam;
   

}

