package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum Priority {
    @Enumerated(EnumType.STRING)
    ALTA, 

    @Enumerated(EnumType.STRING)
    MEDIA,
     
    @Enumerated(EnumType.STRING)
    BAJA;
}
