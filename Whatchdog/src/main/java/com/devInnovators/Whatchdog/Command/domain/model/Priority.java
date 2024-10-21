package com.devInnovators.Whatchdog.Command.domain.model;

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
