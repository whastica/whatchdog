package com.devInnovators.Whatchdog.Command.aplicattion.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDTO {
    
    private String id;
    @NotNull
    private String category;
    @NotNull
    private String priority;
}
