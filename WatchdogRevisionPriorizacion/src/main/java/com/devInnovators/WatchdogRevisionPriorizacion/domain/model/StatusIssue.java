package com.devInnovators.WatchdogRevisionPriorizacion.domain.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum StatusIssue {

    @Enumerated(EnumType.STRING)
    ASIGNADO,

    @Enumerated(EnumType.STRING)
    EN_PROCESO,

    @Enumerated(EnumType.STRING)
    RESUELTO;
}
