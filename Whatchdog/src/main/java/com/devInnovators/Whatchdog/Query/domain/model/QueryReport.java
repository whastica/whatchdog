package com.devInnovators.Whatchdog.Query.domain.model;

import java.time.LocalDateTime;


import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Embedded;
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
    private String idReport;
    private String description;
   
       
    private String idcitizen;
    
    //@DBRef(lazy = false)
    private String idissue;
    private String idAdminC;
    
    @DBRef(lazy = false) 
    private List<QueryComment> comments;

    private QueryStatus status;
    
    private String categoryIssue;

    @Embedded
    private QueryCoordinates coordinates;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String fotoUrl;

  /*   @DBRef(lazy = false)
    @Field("idAdminC")  */
   

    
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
