package com.devInnovators.Whatchdog.Query.domain.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum ResolutionTeam {

    @Enumerated(EnumType.STRING)
    INFRAESTRUCTURA_VIAL,

    @Enumerated(EnumType.STRING)
    SERVICIOS_PUBLICOS,

    @Enumerated(EnumType.STRING)
    SALUD_PUBLICA_Y_MEDIO_AMBIENTE,

    @Enumerated(EnumType.STRING)
    SEGURIDAD_Y_ORDEN_PUBLICO,

    @Enumerated(EnumType.STRING)
    TRANSPORTE_Y_MOVILIDAD,

    @Enumerated(EnumType.STRING)
    PROTECCION_Y_BIENESTAR;
}
