package com.devInnovators.Whatchdog.Query.domain.repository;

import com.devInnovators.Whatchdog.Query.domain.model.QueryReport;


import com.devInnovators.Whatchdog.Query.domain.model.QueryStatus;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface QueryReportRepository extends MongoRepository<QueryReport, String> {
    List<QueryReport> findByStatus(QueryStatus status); 
    List<QueryReport> findByCategoryIssue(String categoryIssue);
    @Query("{ 'idAdminC.id': ?0 }")
    List<QueryReport> findByIdAdminC(String idAdminC);
    QueryReport findByIdReport(String idReport);
        // Verifica si un reporte existe por 'idReport'
    boolean existsByIdReport(String idReport);
        // Elimina un reporte por 'idReport'
    void deleteByIdReport(String idReport);

}
