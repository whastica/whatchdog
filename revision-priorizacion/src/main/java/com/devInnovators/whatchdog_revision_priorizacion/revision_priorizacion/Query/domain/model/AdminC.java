package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<QueryReport> assignedReports;
}
