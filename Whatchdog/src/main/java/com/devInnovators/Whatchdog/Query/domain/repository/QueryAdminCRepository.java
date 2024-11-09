package com.devInnovators.Whatchdog.Query.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.devInnovators.Whatchdog.Query.domain.model.QueryAdminC;

@Repository
public interface QueryAdminCRepository extends MongoRepository<QueryAdminC, String> {
}