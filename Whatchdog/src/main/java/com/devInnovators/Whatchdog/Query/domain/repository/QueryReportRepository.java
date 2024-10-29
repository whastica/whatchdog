package com.devInnovators.Whatchdog.Query.domain.repository;

import com.devInnovators.Whatchdog.Query.domain.model.QueryReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface QueryReportRepository extends MongoRepository<QueryReport, String> {

    List<QueryReport> findByIssueId(String issueId);
}
