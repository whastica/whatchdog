package com.devInnovators.Whatchdog.Query.domain.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Entity;

import com.devInnovators.Whatchdog.Query.domain.model.Report; // Ensure that the Report class exists in this package or update the package path accordingly


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor  // Genera un constructor sin argumentos
@Document(collection = "adminC") 
public class AdminC {
    
    @Id // Indica que este campo es la clave primaria en MongoDB
    private String id;
    
    private String name;

    @DBRef(lazy = true)
    private List<Report> assignedReport;// Esta lista debería tener el getter

}