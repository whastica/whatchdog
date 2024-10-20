package com.devInnovators.Whatchdog.Command.aplicattion.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatesDTO {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

}
