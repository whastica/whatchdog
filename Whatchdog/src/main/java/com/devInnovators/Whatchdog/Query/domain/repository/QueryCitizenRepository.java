package com.devInnovators.Whatchdog.Query.domain.repository;

import com.devInnovators.Whatchdog.Query.domain.model.QueryCitizen;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryCitizenRepository extends MongoRepository<QueryCitizen, String> {
}