package com.devInnovators.WhatchdogRevisionPriorizacion.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devInnovators.WhatchdogRevisionPriorizacion.domain.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, String> {
    
}