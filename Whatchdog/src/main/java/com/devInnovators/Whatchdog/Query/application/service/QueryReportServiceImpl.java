package com.devInnovators.Whatchdog.Query.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;

import com.devInnovators.Whatchdog.Query.application.interfaces.QueryReportServiceInterface;
import com.devInnovators.Whatchdog.Query.application.DTO.CitizenDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.IssueDTO;
import com.devInnovators.Whatchdog.Query.application.DTO.CoordinatesDTO;
import com.devInnovators.Whatchdog.Query.domain.model.Citizen;
import com.devInnovators.Whatchdog.Query.domain.model.Coordinates;
import com.devInnovators.Whatchdog.Query.domain.model.Issue;
import com.devInnovators.Whatchdog.Query.domain.model.Status;

import com.devInnovators.Whatchdog.Query.domain.repository.QueryReportRepository;
import com.devInnovators.Whatchdog.Query.domain.model.Report;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class QueryReportServiceImpl implements QueryReportServiceInterface {

   
    private final QueryReportRepository reportRepository;

    @Autowired
    public QueryReportServiceImpl(QueryReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ReportDTO findReportById(String id) {
        Report report = reportRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found with ID: " + id));
        return convertReportToDTO(report);
    }
    public List<ReportDTO> findAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                      .map(this::convertReportToDTO)
                      .collect(Collectors.toList());
    }

    // Método privado para la conversión de Report a ReportDTO
    private ReportDTO convertReportToDTO(Report report) {
        CitizenDTO citizenDTO = convertCitizenToDTO(report.getCitizen());
        IssueDTO issueDTO = convertIssueToDTO(report.getIssue());
        CoordinatesDTO coordinatesDTO = convertCoordinatesToDTO(report.getCoordinates());

        return new ReportDTO(
                report.getId(),
                report.getDescription(),
                citizenDTO,
                issueDTO,
                report.getStatus(),
                coordinatesDTO,
                report.getCreateDate(),
                report.getUpdateDate(),
                report.getFotoUrl()
        );
    }

    // Métodos privados para convertir entidades anidadas a sus respectivos DTOs
    private CitizenDTO convertCitizenToDTO(Citizen citizen) {
        return new CitizenDTO(
                citizen.getId(),
                citizen.getName(),
                citizen.getEmail(),
                citizen.getPhone()
        );
    }

    private IssueDTO convertIssueToDTO(Issue issue) {
        return new IssueDTO(
                issue.getId(),
                issue.getCategory(),
                issue.getPriority()
        );
    }

    private CoordinatesDTO convertCoordinatesToDTO(Coordinates coordinates) {
        return new CoordinatesDTO(
                coordinates.getLatitude(),
                coordinates.getLongitude()
        );
    }
    
  /*   @Override
    public List<ReportDTO> findReportByEstado(Status status) {
        List<Report> reports = reportRepository.findByEstado(status);
        return reports.stream()
                      .map(this::convertReportToDTO)
                      .collect(Collectors.toList());
    }
 */
   /*  @Override
    // Método que busca un reporte por su ciudadano
    public List<ReportDTO> findReportByCitizenId(String id) {
        List<Report> report = reportRepository.findByCiudadano(id);
                // Verificar si la lista está vacía y lanzar una excepción si no hay reportes
        if (report.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reports found for the given citizen ID");
        }
        return report.stream()
        .map(this::convertReportToDTO)
        .collect(Collectors.toList());
    
    } */
     


}
