package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model;

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
