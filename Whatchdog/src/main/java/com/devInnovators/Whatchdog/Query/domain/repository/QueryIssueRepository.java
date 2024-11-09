package com.devInnovators.Whatchdog.Query.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.devInnovators.Whatchdog.Query.domain.model.QueryIssue;

@Repository
public interface QueryIssueRepository extends MongoRepository<QueryIssue, String> {
}
