package com.devInnovators.WhatchdogRevisionPriorizacion.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devInnovators.WhatchdogRevisionPriorizacion.domain.model.Report;

public interface ReportRepository extends JpaRepository<Report, String> {
    
}
