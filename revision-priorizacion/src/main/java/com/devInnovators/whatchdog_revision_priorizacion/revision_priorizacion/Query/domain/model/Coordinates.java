package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {
    private double latitude;
    private double longitude;
}
