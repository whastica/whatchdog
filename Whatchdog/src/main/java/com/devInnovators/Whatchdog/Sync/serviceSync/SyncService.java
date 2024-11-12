package com.devInnovators.Whatchdog.Sync.serviceSync;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devInnovators.Whatchdog.Command.domain.model.Citizen;
import com.devInnovators.Whatchdog.Command.domain.model.Comment;
import com.devInnovators.Whatchdog.Command.domain.model.Coordinates;
import com.devInnovators.Whatchdog.Command.domain.model.Report;
import com.devInnovators.Whatchdog.Command.domain.model.Status;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandReportRepository;
import com.devInnovators.Whatchdog.Query.domain.model.QueryCitizen;
import com.devInnovators.Whatchdog.Query.domain.model.QueryComment;
import com.devInnovators.Whatchdog.Query.domain.model.QueryCoordinates;
import com.devInnovators.Whatchdog.Query.domain.model.QueryReport;
import com.devInnovators.Whatchdog.Query.domain.model.QueryStatus;
import com.devInnovators.Whatchdog.Query.domain.repository.QueryReportRepository;

import jakarta.transaction.Transactional;

@Service
public class SyncService {

    @Autowired
    private CommandReportRepository commandReportRepository;

    @Autowired
    private QueryReportRepository queryReportRepository;

    
    @Transactional
    public void syncReportById(String reportId) {
        // Buscar el reporte en la base de datos de comandos
        Report commandReport = commandReportRepository.findById(reportId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found in Command database"));

         // Convertir el reporte de comandos a reporte de consulta
        QueryReport queryReport = convertToQueryReport(commandReport);
        // Verificar si el reporte ya existe en MongoDB antes de guardar
        if (queryReportRepository.existsByIdReport(queryReport.getIdReport())) {
            // Si el reporte ya existe, actualizarlo
            QueryReport existingQueryReport = queryReportRepository.findByIdReport(queryReport.getIdReport());
            updateQueryReportFields(existingQueryReport, queryReport);
            queryReportRepository.save(existingQueryReport);
        } else {
            // Si el reporte no existe, insertar un nuevo documento
            queryReportRepository.save(queryReport);
}
        
    }
     // Método para actualizar los campos de un reporte existente en MongoDB
     private void updateQueryReportFields(QueryReport existingQueryReport, QueryReport newQueryReport) {
        existingQueryReport.setDescription(newQueryReport.getDescription());
        existingQueryReport.setIdcitizen(newQueryReport.getIdcitizen());
        existingQueryReport.setIdissue(newQueryReport.getIdissue());
        existingQueryReport.setStatus(newQueryReport.getStatus());
        existingQueryReport.setCoordinates(newQueryReport.getCoordinates());
        existingQueryReport.setUpdateDate(newQueryReport.getUpdateDate());
        existingQueryReport.setFotoUrl(newQueryReport.getFotoUrl());
    }

    // Método para sincronizar todos los reportes de la base de datos de comandos a la de consulta
    @Transactional
    public void syncAllReports() {
        // Obtener todos los reportes de la base de datos de comandos
        List<Report> commandReports = commandReportRepository.findAll();

            // Convertir y guardar cada reporte en MongoDB
        for (Report commandReport : commandReports) {
            // Convertir el reporte de la base de datos de comandos a un reporte de consulta
            QueryReport queryReport = convertToQueryReport(commandReport);

           
            // Verificar si el reporte ya existe en MongoDB antes de guardar
            if (queryReportRepository.existsByIdReport(queryReport.getIdReport())) {
                // Si el reporte ya existe, actualizarlo 
                QueryReport existingQueryReport = queryReportRepository.findByIdReport(queryReport.getIdReport());
                updateQueryReportFields(existingQueryReport, queryReport);
                queryReportRepository.save(existingQueryReport);
            } else {
                // Si el reporte no existe, insertar un nuevo documento
                queryReportRepository.save(queryReport);
            }
            
        }
    }

    // Método para eliminar un reporte de MongoDB por su ID
    @Transactional
    public void deleteReportFromMongoById(String idReport) {
        // Verificar si el reporte existe en la base de datos de consulta antes de eliminar
        if (queryReportRepository.existsByIdReport(idReport)) {
            queryReportRepository.deleteByIdReport(idReport);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found in MongoDB");
        }
    } 
 
    // Conversión de CommandReport a QueryReport
    private QueryReport convertToQueryReport(Report commandReport) {
        QueryReport queryReport = new QueryReport();
        queryReport.setIdReport(commandReport.getIdReport());
        queryReport.setDescription(commandReport.getDescription());
        queryReport.setIdcitizen(commandReport.getCitizen().getId()); // Conversión de Citizen
         // Verificación si Issue es null antes de asignarlo
        if (commandReport.getIssue() != null) {
            queryReport.setIdissue(commandReport.getIssue().getId()); // Conversión de Issue
        } else {
            queryReport.setIdissue(null);  // Asignar null si Issue es null
        }
        queryReport.setIdAdminC(commandReport.getAdminC().getId());
        queryReport.setComments(convertToQueryCommentsList(commandReport.getComments()));
        queryReport.setStatus(convertToQueryStatus(commandReport.getStatus())); // Conversión de Status
        queryReport.setCategoryIssue(commandReport.getCategoryIssue().name());
        queryReport.setCoordinates(convertToQueryCoordinates(commandReport.getCoordinates())); // Conversión de Coordinates
        queryReport.setCreateDate(commandReport.getCreateDate());
        queryReport.setUpdateDate(commandReport.getUpdateDate());
        queryReport.setFotoUrl(commandReport.getFotoUrl());
        queryReport.setNumLikes(commandReport.getNumLikes());
        queryReport.setNumDislikes(commandReport.getNumDislikes());
        return queryReport;
    }
    // Conversión de Status de Command a Query
    private QueryStatus convertToQueryStatus(Status commandStatus) {
        return QueryStatus.valueOf(commandStatus.name());
    }

    // Conversión de Coordinates
    private QueryCoordinates convertToQueryCoordinates(Coordinates commandCoordinates) {
        QueryCoordinates queryCoordinates = new QueryCoordinates();
        queryCoordinates.setLatitude(commandCoordinates.getLatitude());
        queryCoordinates.setLongitude(commandCoordinates.getLongitude());
        return queryCoordinates;
    }

    // Conversión de Comments
    private QueryComment convertToQueryComments(Comment commandComments) {
        QueryComment queryComments = new QueryComment();
        queryComments.setId(commandComments.getId());
        queryComments.setDescription(commandComments.getDescription());
        queryComments.setIdcitizen(convertToQueryCitizen(commandComments.getCitizenId()));
        queryComments.setCreateDate(commandComments.getCreateDate());
  
        
    // Buscar el reporte correspondiente en MongoDB por el id
        QueryReport queryReport = queryReportRepository.findById(commandComments.getReport().getIdReport())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found"));

        queryComments.setIdreport(queryReport);  // Ahora se pasa un objeto QueryRepo
        return queryComments;
    }

    // Conversión de Comments a lista
    private List<QueryComment> convertToQueryCommentsList(List<Comment> commandComments) {
        List<QueryComment> queryComments = new ArrayList<>();
        for (Comment comment : commandComments) {
            queryComments.add(convertToQueryComments(comment));
        }
        return queryComments;
    }

    // Conversión de Citizen a QueryCitizen
    private QueryCitizen convertToQueryCitizen(Citizen commandCitizen) {
        QueryCitizen queryCitizen = new QueryCitizen();
        queryCitizen.setId(commandCitizen.getId());
        queryCitizen.setName(commandCitizen.getName());
        queryCitizen.setEmail(commandCitizen.getEmail());
        queryCitizen.setPhone(commandCitizen.getPhone());
        return queryCitizen;
    }


    
}