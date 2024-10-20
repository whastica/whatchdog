package com.devInnovators.Whatchdog.Command.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Citizen {
    private String id;
    private String name;
    private String email;

    // operaciones o metodos de dominio
}
