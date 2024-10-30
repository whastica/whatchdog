package com.devInnovators.Whatchdog.Query.domain.model;

import java.time.LocalDateTime;


import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data              
@AllArgsConstructor     
@NoArgsConstructor 
@Document(collection = "report") 

public class QueryReport {
    @Id
    private String id;
    private String description;
   
    @DBRef(lazy = true)   
    private Citizen idcitizen;
    
    @DBRef(lazy = true)
    private Issue idissue;

    @DBRef(lazy = true) 
    private Status status;

    @DBRef(lazy = true) 
    private Coordinates coordinates;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String fotoUrl;

    @DBRef(lazy = true) 
    private AdminC idAdminC;

    @DBRef(lazy = true) 
    private CategoryIssue categoryIssue;
    
    @DBRef(lazy = true) 
    private List<Comment> comments;
    private Long numLikes;
    private Long numDislikes;


    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createDate = now;
        this.updateDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
    }

    public void setCitizen(com.devInnovators.Whatchdog.Command.domain.model.Citizen citizen) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCitizen'");
    }

    public void setStatus(com.devInnovators.Whatchdog.Command.domain.model.Status status2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStatus'");
    }

    public void setCoordinates(com.devInnovators.Whatchdog.Command.domain.model.Coordinates coordinates2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCoordinates'");
    }

    public void setProblem(Object problem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setProblem'");
    }

    public Citizen getCitizen() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCitizen'");
    }

    public Issue getIssue() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIssue'");
    }

}
