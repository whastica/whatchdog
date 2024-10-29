package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.repository;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.Issue;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.Priority;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.ResolutionTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandIssueRepository extends JpaRepository<Issue, String> {
    // Método para encontrar todos los issues con una prioridad específica
    List<Issue> findByPriority(Priority priority);

    // Método para encontrar todos los issues asignados a un equipo de resolución específico
    List<Issue> findByResolutionTeam(ResolutionTeam resolutionTeam);
}
