package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.aplication.interfaces;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.aplication.DTO.IssueDTO;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.Priority;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.ResolutionTeam;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.StatusIssue;

import java.util.List;

public interface CommandPriorityServiceInterface {

    // Crear un nuevo Issue
    IssueDTO createIssue(IssueDTO issueDTO);

    // Editar prioridad de Issue
    IssueDTO updatePriority(String issueId, String priority);

    // Editar status de Issue
    IssueDTO updateStatus(String issueId, String status);

    // Actualizar un Issue existente
    IssueDTO updateIssue(String issueId, IssueDTO issueDTO);

    // Obtener todos los Issues con prioridad específica
    List<IssueDTO> getIssuesByPriority(String priority);

    // Cambiar el equipo de resolución de un Issue
    IssueDTO assignResolutionTeam(String issueId, ResolutionTeam resolutionTeam);

    // Consultar Issues asignados a un equipo específico
    List<IssueDTO> getIssuesByResolutionTeam(String resolutionTeam);

    // Editar la prioridad de un Issue
    IssueDTO updateIssuePriority(String issueId, Priority priority);

    // Editar el estado de un Issue
    IssueDTO updateIssueStatus(String issueId, StatusIssue status);
}
