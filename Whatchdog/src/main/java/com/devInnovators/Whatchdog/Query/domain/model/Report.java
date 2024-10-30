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

public class Report {
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

}