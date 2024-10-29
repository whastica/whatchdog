package com.devInnovators.Whatchdog.Command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devInnovators.Whatchdog.Command.domain.model.Report;

public interface CommandReportRepository extends JpaRepository<Report, String> {
    
}
