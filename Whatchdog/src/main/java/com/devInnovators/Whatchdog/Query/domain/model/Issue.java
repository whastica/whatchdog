package com.devInnovators.Whatchdog.Query.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document       
public class Issue {
    @Id
    private String id;
    private String description;
    private String category;
    private String priority;
    // operaciones o metodos de dominio

}

