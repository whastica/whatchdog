package com.devInnovators.Whatchdog.Query.domain.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comment")
public class QueryComment {
    @Id
    private String id;
    private String description;
    @DBRef(lazy = true) 
    private QueryCitizen idcitizen;
    private LocalDateTime createDate;
    @DBRef(lazy = true) 
    private QueryReport idreport;
    
}
