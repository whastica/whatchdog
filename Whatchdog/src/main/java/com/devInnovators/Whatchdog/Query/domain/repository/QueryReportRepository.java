package com.devInnovators.Whatchdog.Query.domain.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.devInnovators.Whatchdog.Query.domain.model.ReportQuery;


@Repository
public interface QueryReportRepository extends MongoRepository<ReportQuery, String> {
    List<ReportQuery> findByCitizenId(String citizenId);
    List<ReportQuery> findByIssueId(String issueId);
}
