package com.devInnovators.Whatchdog.Command.domain.model;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "adminC", fetch = FetchType.LAZY)
    private List<Report> asignedReports;

}
