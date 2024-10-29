package com.devInnovators.Whatchdog.Command.infra;

import com.devInnovators.Whatchdog.Command.aplication.DTO.IssueDTO;
import com.devInnovators.Whatchdog.Command.aplication.interfaces.CommandPriorityServiceInterface;
import com.devInnovators.Whatchdog.Command.domain.model.ResolutionTeam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/command/issue")
public class CommandIssueController {

    @Autowired
    private CommandPriorityServiceInterface priorityService;

    // Endpoint para crear un nuevo Issue
    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueDTO issueDTO) {
        IssueDTO createdIssue = priorityService.createIssue(issueDTO);
        return new ResponseEntity<>(createdIssue, HttpStatus.CREATED);
    }

    // Editar la prioridad de un Issue
    @PutMapping("/{issueId}/priority/{priority}")
    public ResponseEntity<IssueDTO> updateIssuePriority(@PathVariable String issueId, @PathVariable String priority) {
        IssueDTO updatedIssue = priorityService.updatePriority(issueId, priority);
        return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
    }

    // Editar el estado de un Issue
    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<IssueDTO> updateIssueStatus(@PathVariable String issueId, @PathVariable String status) {
        IssueDTO updatedIssue = priorityService.updateStatus(issueId, status);
        return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
    }

    // Endpoint para actualizar un Issue
    @PutMapping("/{issueId}")
    public ResponseEntity<IssueDTO> updateIssue(@PathVariable String issueId, @RequestBody IssueDTO issueDTO) {
        IssueDTO updatedIssue = priorityService.updateIssue(issueId, issueDTO);
        return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
    }

    // Endpoint para obtener Issues por prioridad
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<IssueDTO>> getIssuesByPriority(@PathVariable String priority) {
        List<IssueDTO> issues = priorityService.getIssuesByPriority(priority);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    // Endpoint para asignar un equipo de resolución a un Issue
    @PutMapping("/{issueId}/assignResolutionTeam/{resolutionTeam}")
    public ResponseEntity<IssueDTO> assignResolutionTeam(@PathVariable String issueId, @PathVariable String resolutionTeam) {
        // Conversión de String a ResolutionTeam
        ResolutionTeam team = ResolutionTeam.valueOf(resolutionTeam.toUpperCase());
        IssueDTO updatedIssue = priorityService.assignResolutionTeam(issueId, team);
        return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
    }

    // Endpoint para obtener Issues asignados a un equipo de resolución
    @GetMapping("/resolutionTeam/{resolutionTeam}")
    public ResponseEntity<List<IssueDTO>> getIssuesByResolutionTeam(@PathVariable String resolutionTeam) {
        List<IssueDTO> issues = priorityService.getIssuesByResolutionTeam(resolutionTeam);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }
}
