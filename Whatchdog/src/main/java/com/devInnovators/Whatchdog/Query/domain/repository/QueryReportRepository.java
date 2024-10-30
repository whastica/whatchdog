package com.devInnovators.Whatchdog.Query.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.devInnovators.Whatchdog.Query.domain.model.Report;
import com.devInnovators.Whatchdog.Query.domain.model.Status;

@Repository
public interface QueryReportRepository extends MongoRepository<Report, String> {
    List<Report> findByStatus(Status status); 
    List<Report> findByCategoryIssue(String categoryIssue);
    @Query("{'AdminC.id': ?0 }")
    List<Report> findByIdAdminC(String idAdminC);
}
