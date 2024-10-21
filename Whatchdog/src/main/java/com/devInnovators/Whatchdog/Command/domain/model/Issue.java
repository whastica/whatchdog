package com.devInnovators.Whatchdog.Command.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Generación automática del ID
    private String id;

    @NotNull(message = "La categoría no puede ser nula")
    //@Size(min = 3, max = 50, message = "La categoría debe tener entre 3 y 50 caracteres")
    private String category;

    @Enumerated(EnumType.STRING)  // Guarda el enum como una cadena en la base de datos
    @NotNull(message = "La prioridad no puede ser nula")
    private Priority priority;

    // Métodos de dominio adicionales según tus necesidades
}
