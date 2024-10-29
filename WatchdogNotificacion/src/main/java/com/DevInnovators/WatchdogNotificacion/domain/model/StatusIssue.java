package com.DevInnovators.WatchdogNotificacion.domain.model;

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
