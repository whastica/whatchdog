package com.devInnovators.Whatchdog.Sync.serviceSync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devInnovators.Whatchdog.Command.domain.model.Report;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandReportRepository;
import com.devInnovators.Whatchdog.Query.domain.model.QueryReport;
import com.devInnovators.Whatchdog.Query.domain.repository.QueryReportRepository;

@Service
public class SyncService {

    @Autowired
    private CommandReportRepository commandReportRepository;

    @Autowired
    private QueryReportRepository queryReportRepository;

    // Método para sincronizar un solo reporte por su ID
    public void syncReportById(String reportId) {
        Report commandReport = commandReportRepository.findById(reportId)
            .orElseThrow(() -> new IllegalArgumentException("Report not found in Command database"));

        QueryReport queryReport = new QueryReport();
        queryReport.setId(commandReport.getId());
        queryReport.setDescription(commandReport.getDescription());
        queryReport.setCitizen(commandReport.getCitizen());
        queryReport.setProblem(commandReport.getProblem());
        queryReport.setStatus(commandReport.getStatus());
        queryReport.setCoordinates(commandReport.getCoordinates());
        queryReport.setCreateDate(commandReport.getCreateDate());
        queryReport.setUpdateDate(commandReport.getUpdateDate());
        queryReport.setFotoUrl(commandReport.getFotoUrl());

        queryReportRepository.save(queryReport); // Guardar en MongoDB
    }

    // Método para sincronizar todos los reportes
    public void syncAllReports() {
        Iterable<Report> commandReports = commandReportRepository.findAll();

        for (Report commandReport : commandReports) {
            QueryReport queryReport = new QueryReport();
            queryReport.setId(commandReport.getId());
            queryReport.setDescription(commandReport.getDescription());
            queryReport.setCitizen(commandReport.getCitizen());
            queryReport.setProblem(commandReport.getProblem());
            queryReport.setStatus(commandReport.getStatus());
            queryReport.setCoordinates(commandReport.getCoordinates());
            queryReport.setCreateDate(commandReport.getCreateDate());
            queryReport.setUpdateDate(commandReport.getUpdateDate());
            queryReport.setFotoUrl(commandReport.getFotoUrl());

            queryReportRepository.save(queryReport); // Guardar en MongoDB
        }
    }

    // Nuevo método: Eliminar reporte de MongoDB por ID
    public void deleteReportFromMongoById(String reportId) {
        // Verificar si el reporte existe en MongoDB antes de eliminar
        if (queryReportRepository.existsById(reportId)) {
            queryReportRepository.deleteById(reportId);
        } else {
            throw new IllegalArgumentException("Report not found in MongoDB");
        }
    }
}