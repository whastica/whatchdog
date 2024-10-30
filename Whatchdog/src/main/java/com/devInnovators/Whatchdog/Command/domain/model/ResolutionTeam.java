package com.devInnovators.Whatchdog.Command.domain.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum ResolutionTeam {

    @Enumerated(EnumType.STRING)
    INFRAESTRUCTURA_Y_OBRAS_PUBLICAS,

    @Enumerated(EnumType.STRING)
    SERVICIOS_PUBLICOS,

    @Enumerated(EnumType.STRING)
    MEDIO_AMBIENTE,

    @Enumerated(EnumType.STRING)
    SEGURIDAD_CIUDADANA,

    @Enumerated(EnumType.STRING)
    MOVILIDAD_Y_TRANSPORTE;
}
