package com.devInnovators.Whatchdog.Command.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem {
    private String id;
    private String description;
    private String category;

    // operaciones o metodos de dominio

}
