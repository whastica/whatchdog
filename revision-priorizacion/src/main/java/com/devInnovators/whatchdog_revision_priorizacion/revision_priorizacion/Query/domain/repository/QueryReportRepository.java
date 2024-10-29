package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.repository;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model.QueryReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface QueryReportRepository extends MongoRepository<QueryReport, String> {

    List<QueryReport> findByIssueId(String issueId);
}
