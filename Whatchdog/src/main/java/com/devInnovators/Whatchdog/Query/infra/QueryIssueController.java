package com.devInnovators.Whatchdog.Query.infra;

import com.devInnovators.Whatchdog.Query.aplication.DTO.IssueDTO;
import com.devInnovators.Whatchdog.Query.aplication.interfaces.QueryIssueServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/query/issue")
public class QueryIssueController {

    @Autowired
    private QueryIssueServiceInterface issueService;

    @GetMapping
    public ResponseEntity<List<IssueDTO>> getAllIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDTO> getIssueById(@PathVariable String issueId) {
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<IssueDTO>> getIssuesByPriority(@PathVariable String priority) {
        return ResponseEntity.ok(issueService.getIssuesByPriority(priority));
    }

    @GetMapping("/resolutionTeam/{resolutionTeam}")
    public ResponseEntity<List<IssueDTO>> getIssuesByResolutionTeam(@PathVariable String resolutionTeam) {
        return ResponseEntity.ok(issueService.getIssuesByResolutionTeam(resolutionTeam));
    }
}
