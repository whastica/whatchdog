package com.devInnovators.Whatchdog.Query.domain.repository;

import com.devInnovators.Whatchdog.Query.domain.model.Report;

import com.devInnovators.Whatchdog.Query.domain.model.Status;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QueryReportRepository extends MongoRepository<Report, String> {

   // List<Report> findByEstado(Status status);
    //List<Report> findByCiudadano(String id);
}
