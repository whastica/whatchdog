package com.DevInnovators.WatchdogNotificacion.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DevInnovators.WatchdogNotificacion.domain.model.Issue;
import com.github.andrewoma.dexx.collection.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {
    // Método para obtener todos los issues asignados a un administrador específico
    List<Issue> findByIdAdminc(String idAdminc);

    /*  Método para obtener issues por categoría
    List<Issue> findByCategoryIssue(String categoryIssue);

    // Método para obtener issues por prioridad
    List<Issue> findByPriority(Priority priority);

    // Método para obtener issues por estado
    List<Issue> findByStatusIssue(StatusIssue statusIssue);*/
}