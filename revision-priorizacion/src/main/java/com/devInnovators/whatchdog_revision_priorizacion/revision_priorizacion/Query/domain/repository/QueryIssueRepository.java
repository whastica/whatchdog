package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.repository;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model.QueryIssue;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface QueryIssueRepository extends MongoRepository<QueryIssue, String> {

    List<QueryIssue> findByPriority(String priority);

    List<QueryIssue> findByResolutionTeam(String resolutionTeam);
}
