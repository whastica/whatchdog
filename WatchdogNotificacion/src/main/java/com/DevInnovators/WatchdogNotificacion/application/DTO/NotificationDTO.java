package com.DevInnovators.WatchdogNotificacion.application.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private String id;
    private String description;
    private LocalDateTime createDate;
    private String idAdmin;
    private String idIssue;
    private String idReport;
    private String idCitizen;
}
