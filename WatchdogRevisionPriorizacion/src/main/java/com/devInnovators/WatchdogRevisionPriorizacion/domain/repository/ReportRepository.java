package com.devInnovators.WatchdogRevisionPriorizacion.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Report;

public interface  ReportRepository extends JpaRepository<Report, String> {
    
}
