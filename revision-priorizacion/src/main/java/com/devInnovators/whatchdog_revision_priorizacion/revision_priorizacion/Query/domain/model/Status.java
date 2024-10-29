package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum Status {

    @Enumerated(EnumType.STRING)
    EN_LISTA,

    @Enumerated(EnumType.STRING)
    EN_REVISION,

    @Enumerated(EnumType.STRING)
    EN_PROCESO,

    @Enumerated(EnumType.STRING)
    RESUELTO;
}
