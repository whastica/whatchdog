package com.devInnovators.Whatchdog.Command.aplicattion.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDTO {
    private String id;
    @NotNull
    private String name;
    private String email;
    private String phone; 
}
