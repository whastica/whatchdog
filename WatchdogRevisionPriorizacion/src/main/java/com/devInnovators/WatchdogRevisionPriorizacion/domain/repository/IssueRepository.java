package com.devInnovators.WatchdogRevisionPriorizacion.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Issue;

public interface  IssueRepository extends JpaRepository<Issue, String> {
    // Aquí puedes agregar métodos adicionales de consulta si es necesario
}
