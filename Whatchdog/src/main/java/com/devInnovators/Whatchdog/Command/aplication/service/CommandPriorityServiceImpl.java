package com.devInnovators.Whatchdog.Command.aplication.service;

import com.devInnovators.Whatchdog.Command.aplication.DTO.IssueDTO;
import com.devInnovators.Whatchdog.Command.aplication.interfaces.CommandPriorityServiceInterface;
import com.devInnovators.Whatchdog.Command.domain.model.Issue;
import com.devInnovators.Whatchdog.Command.domain.model.Priority;
import com.devInnovators.Whatchdog.Command.domain.model.ResolutionTeam;
import com.devInnovators.Whatchdog.Command.domain.model.StatusIssue;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandIssueRepository;
import com.devInnovators.Whatchdog.Command.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandPriorityServiceImpl implements CommandPriorityServiceInterface {

    @Autowired
    private CommandIssueRepository issueRepository;

    @Override
    public IssueDTO createIssue(IssueDTO issueDTO) {
        Issue issue = new Issue(issueDTO);
        issue = issueRepository.save(issue);
        return new IssueDTO(issue);
    }

    @Override
    public IssueDTO updateIssue(String issueId, IssueDTO issueDTO) {
        Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id " + issueId));
        issue.updateFromDTO(issueDTO);
        issue = issueRepository.save(issue);
        return new IssueDTO(issue);
    }

    @Override
    public IssueDTO updatePriority(String issueId, String priority) {
        return updateIssuePriority(issueId, Priority.valueOf(priority.toUpperCase()));
    }

    @Override
    public IssueDTO updateStatus(String issueId, String status) {
        return updateIssueStatus(issueId, StatusIssue.valueOf(status.toUpperCase()));
    }

    @Override
    public List<IssueDTO> getIssuesByPriority(String priority) {
        Priority issuePriority = Priority.valueOf(priority.toUpperCase());
        return issueRepository.findByPriority(issuePriority).stream()
            .map(IssueDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public IssueDTO assignResolutionTeam(String issueId, ResolutionTeam resolutionTeam) {
        Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id " + issueId));
        issue.setResolutionTeam(resolutionTeam);
        issue = issueRepository.save(issue);
        return new IssueDTO(issue);
    }

    @Override
    public List<IssueDTO> getIssuesByResolutionTeam(String resolutionTeam) {
        ResolutionTeam team = ResolutionTeam.valueOf(resolutionTeam.toUpperCase());
        return issueRepository.findByResolutionTeam(team).stream()
            .map(IssueDTO::new)
            .collect(Collectors.toList());
    }

    // Implementación de updateIssuePriority
    @Override
    public IssueDTO updateIssuePriority(String issueId, Priority priority) {
        Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id " + issueId));
        issue.setPriority(priority);
        issue = issueRepository.save(issue);
        return new IssueDTO(issue);
    }

    // Implementación de updateIssueStatus
    @Override
    public IssueDTO updateIssueStatus(String issueId, StatusIssue status) {
        Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id " + issueId));
        issue.setStatus(status);
        issue = issueRepository.save(issue);
        return new IssueDTO(issue);
    }
}
