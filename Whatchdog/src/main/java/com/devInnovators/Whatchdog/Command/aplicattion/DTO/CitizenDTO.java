package com.devInnovators.Whatchdog.Command.aplicattion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDTO {
    private String id;
    private String name;
    private String email;
    private String phone; 
}
