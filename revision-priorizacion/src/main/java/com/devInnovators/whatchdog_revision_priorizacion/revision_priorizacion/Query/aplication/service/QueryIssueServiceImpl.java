package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.service;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.DTO.IssueDTO;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.interfaces.QueryIssueServiceInterface;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model.QueryIssue;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.repository.QueryIssueRepository;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryIssueServiceImpl implements QueryIssueServiceInterface {

    @Autowired
    private QueryIssueRepository issueRepository;

    @Override
    public List<IssueDTO> getAllIssues() {
        return issueRepository.findAll().stream()
            .map(IssueDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public IssueDTO getIssueById(String issueId) {
        QueryIssue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id " + issueId));
        return new IssueDTO(issue);
    }

    @Override
    public List<IssueDTO> getIssuesByPriority(String priority) {
        return issueRepository.findByPriority(priority).stream()
            .map(IssueDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<IssueDTO> getIssuesByResolutionTeam(String resolutionTeam) {
        return issueRepository.findByResolutionTeam(resolutionTeam).stream()
            .map(IssueDTO::new)
            .collect(Collectors.toList());
    }
}
