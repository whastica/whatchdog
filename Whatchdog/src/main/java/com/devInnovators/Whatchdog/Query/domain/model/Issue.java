package com.devInnovators.Whatchdog.Query.domain.model;

import org.hibernate.engine.spi.Resolution;
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
public class Issue {
    @Id
    private String id;
   
    private String categoryIssue;
    @DBRef(lazy = true) 
    private StatusIssue statusIssue;
    private String priority;
    @DBRef(lazy = true) 
    private List<QueryReport> reportsList;
    @DBRef(lazy = true) 
    private AdminC idAdminC;
    @DBRef(lazy = true) 
    private ResolutionTeam resolutionTeam;
    // operaciones o metodos de dominio
    public String getCategory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategory'");
    }

}

