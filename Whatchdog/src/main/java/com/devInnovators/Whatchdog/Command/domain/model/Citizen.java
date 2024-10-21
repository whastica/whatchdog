package com.devInnovators.Whatchdog.Command.domain.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor // Constructor por defecto
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Genera el ID automáticamente
    private String id;

    @NotNull(message = "El nombre no puede ser nulo")
    //@Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @Email(message = "El email debe ser válido")
    @NotNull(message = "El email no puede ser nulo")
    private String email;
    
    //@Pattern(regexp = "^[+]?[0-9]{10,13}$", message = "El teléfono debe ser válido")
    private String phone;
}