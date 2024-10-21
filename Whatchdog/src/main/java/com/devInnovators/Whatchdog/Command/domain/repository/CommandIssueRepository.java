package com.devInnovators.Whatchdog.Command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devInnovators.Whatchdog.Command.domain.model.Issue;

public interface CommandIssueRepository extends JpaRepository<Issue, String> {
    // Aquí puedes agregar métodos adicionales de consulta si es necesario
}
