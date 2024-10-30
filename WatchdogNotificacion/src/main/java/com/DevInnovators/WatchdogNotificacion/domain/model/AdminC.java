package com.DevInnovators.WatchdogNotificacion.domain.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminC {

    @Id
    private String id;
    @NotNull
    private String name;

    private List<Report> AsignedReports;

}
