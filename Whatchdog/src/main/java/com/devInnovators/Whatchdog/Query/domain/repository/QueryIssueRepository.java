package com.devInnovators.Whatchdog.Query.domain.repository;

import com.devInnovators.Whatchdog.Query.domain.model.QueryIssue;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface QueryIssueRepository extends MongoRepository<QueryIssue, String> {

    List<QueryIssue> findByPriority(String priority);

    List<QueryIssue> findByResolutionTeam(String resolutionTeam);
}
