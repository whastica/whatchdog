package com.devInnovators.Whatchdog.Query.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.devInnovators.Whatchdog.Query.domain.model.QueryReport;

public interface QueryReportRepository extends MongoRepository<QueryReport, String> {
    // Aquí puedes definir métodos personalizados si es necesario
}
