package com.devInnovators.Whatchdog.Sync.serviceSync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devInnovators.Whatchdog.Command.domain.model.Report;
// import com.devInnovators.Whatchdog.Command.domain.model.Report;

import com.devInnovators.Whatchdog.Command.domain.repository.CommandReportRepository;

import com.devInnovators.Whatchdog.Query.domain.repository.QueryReportRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class SyncService {

    @Autowired
    private CommandReportRepository commandReportRepository;

    @Autowired
    private QueryReportRepository queryReportRepository;

  /* 
    @Transactional
    public void syncReportById(String reportId) {
        // Buscar el reporte en la base de datos de comandos
        com.devInnovators.Whatchdog.Command.domain.model.Report commandReport = commandReportRepository.findById(reportId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found in Command database"));

        // Copiar datos a un nuevo objeto Report para MongoDB
        com.devInnovators.Whatchdog.Query.domain.model.Report queryReport = new com.devInnovators.Whatchdog.Query.domain.model.Report();
        queryReport.setId(commandReport.getId()); // Asigna el ID
        queryReport.setDescription(commandReport.getDescription());
        queryReport.setCitizen(commandReport.getCitizen());    // Asigna el ciudadano
        queryReport.setIdissue(commandReport.getIssue());     // Asigna el issue
        queryReport.setStatus(commandReport.getStatus());
        queryReport.setCoordinates(commandReport.getCoordinates());
        queryReport.setCreateDate(commandReport.getCreateDate());
        queryReport.setUpdateDate(commandReport.getUpdateDate());
        queryReport.setFotoUrl(commandReport.getFotoUrl());
        queryReport.setIdAdminc(commandReport.getAdminc());    // Asigna el administrador
        queryReport.setCategoryIssue(commandReport.getCategoryIssue()); // Asigna la categoría del problema
        queryReport.setComments(commandReport.getComments());  // Asigna los comentarios
        queryReport.setNumLikes(commandReport.getNumLikes());
        queryReport.setNumDislikes(commandReport.getNumDislikes());

        // Guardar en la base de datos de consultas (MongoDB)
        queryReportRepository.save(queryReport);
    }

    // Método para sincronizar todos los reportes de la base de datos de comandos a la de consulta
    @Transactional
    public void syncAllReports() {
        // Obtener todos los reportes de la base de datos de comandos
        List<Report> commandReports = commandReportRepository.findAll();

        // Convertir y guardar cada reporte en MongoDB
        for (Report commandReport : commandReports) {
            Report queryReport = new Report();
            queryReport.setId(commandReport.getId());
            queryReport.setDescription(commandReport.getDescription());
            queryReport.setCitizen(commandReport.getCitizen());
            queryReport.setIssue(commandReport.getIssue());
            queryReport.setStatus(commandReport.getStatus());
            queryReport.setCoordinates(commandReport.getCoordinates());
            queryReport.setCreateDate(commandReport.getCreateDate());
            queryReport.setUpdateDate(commandReport.getUpdateDate());
            queryReport.setFotoUrl(commandReport.getFotoUrl());
            queryReport.setAdminc(commandReport.getAdminc());
            queryReport.setCategoryIssue(commandReport.getCategoryIssue());
            queryReport.setComments(commandReport.getComments());
            queryReport.setNumLikes(commandReport.getNumLikes());
            queryReport.setNumDislikes(commandReport.getNumDislikes());

            queryReportRepository.save(queryReport)
        }
    }

    // Método para eliminar un reporte de MongoDB por su ID
    @Transactional
    public void deleteReportFromMongoById(String reportId) {
        // Verificar si el reporte existe en la base de datos de consulta antes de eliminar
        if (queryReportRepository.existsById(reportId)) {
            queryReportRepository.deleteById(reportId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found in MongoDB");
        }
    } */
}