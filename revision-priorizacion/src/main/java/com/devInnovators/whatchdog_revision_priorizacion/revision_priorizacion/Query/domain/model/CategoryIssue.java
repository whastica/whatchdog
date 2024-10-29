package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum CategoryIssue {

    @Enumerated(EnumType.STRING)
    INFRAESTRUCTURA_VIAL,

    @Enumerated(EnumType.STRING)
    ILUMINACION_PUBLICA,

    @Enumerated(EnumType.STRING)
    GESTION_DE_RESIDUOS,

    @Enumerated(EnumType.STRING)
    ESPACIOS_VERDES_Y_PARQUES,

    @Enumerated(EnumType.STRING)
    TRANSPORTE_PUBLICO,

    @Enumerated(EnumType.STRING)
    AGUA_Y_DRENAJE,

    @Enumerated(EnumType.STRING)
    SEGURIDAD_CIUDADANA,

    @Enumerated(EnumType.STRING)
    CONTAMINACION,

    @Enumerated(EnumType.STRING)
    SERVICIOS_PUBLICOS,

    @Enumerated(EnumType.STRING)
    EDIFICACIONES_Y_OBRAS,

    @Enumerated(EnumType.STRING)
    MOVILIDAD_Y_ACCESIBILIDAD,

    @Enumerated(EnumType.STRING)
    ANIMALES,

    @Enumerated(EnumType.STRING)
    INSEGURIDAD_VIAL,

    @Enumerated(EnumType.STRING)
    MOBILIARIO_URBANO;
}
