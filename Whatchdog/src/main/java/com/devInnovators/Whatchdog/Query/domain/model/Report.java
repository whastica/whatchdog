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

@Data              
@AllArgsConstructor     
@NoArgsConstructor 
@Document  

public class Report {
    @Id
    private String id;
    private String description;
    
    @DBRef(lazy = true)   
    private Citizen citizen;
    
    @DBRef(lazy = true)
    private Issue issue;
    
    private Status status;
    private Coordinates coordinates;
    
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String fotoUrl;

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
