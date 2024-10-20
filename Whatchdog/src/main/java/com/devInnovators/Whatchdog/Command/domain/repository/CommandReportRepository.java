package com.devInnovators.Whatchdog.Command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devInnovators.Whatchdog.Command.domain.model.Report;

@Repository
public interface CommandReportRepository extends JpaRepository<Report, String> {
    // Aquí puedes definir métodos personalizados si es necesario

    // Ejemplo: Encuentra reportes por estado
    //List<Report> findByEstado(Status status);
}
