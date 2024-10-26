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
public class Citizen {
    
    @Id // Indica que este campo es la clave primaria en MongoDB
    private String id;
    
    private String name;
    private String email;
    private String phone;

}
