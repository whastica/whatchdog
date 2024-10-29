package com.devInnovators.Whatchdog.Command.domain.repository;

import com.devInnovators.Whatchdog.Command.domain.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandReportRepository extends JpaRepository<Report, String> {
    // Método para encontrar todos los reportes por un issue específico
    List<Report> findByIssueId(String issueId);
}
