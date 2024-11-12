package com.devInnovators.Whatchdog.Command.aplicattion.EventsDTO;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteReportEvent implements Serializable {
    private String id; // ID del reporte que se va a eliminar
}