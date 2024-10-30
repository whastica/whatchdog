package com.DevInnovators.WatchdogNotificacion.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DevInnovators.WatchdogNotificacion.domain.model.CategoryIssue;
import com.DevInnovators.WatchdogNotificacion.domain.model.Report;

import ch.qos.logback.core.status.Status;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {

    // Método para encontrar reportes por ciudadano
    List<Report> findByCitizenId(String citizenId);

    // Método para encontrar reportes por administrador asignado
    List<Report> findByAdmincId(String admincId);

    // Método para encontrar reportes por estado
    List<Report> findByStatus(Status status);

    // Método para encontrar reportes por categoría
    List<Report> findByCategoryIssue(CategoryIssue categoryIssue);


    // Método para encontrar reportes con base en la cantidad de likes (ejemplo de filtro)
    List<Report> findByNumLikesGreaterThanEqual(Long minLikes);
}