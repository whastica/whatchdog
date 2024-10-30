package com.DevInnovators.WatchdogNotificacion.application.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCDTO {

    private String id;

    private String name;

    // Lista de IDs de los reportes asignados o un DTO simplificado de Report si es necesario
    private List<String> assignedReportIds;
}