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
    // Método que convierte un reporte a un DTO
    private ReportDTO convertReportToDTO(Report report) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(report.getId());
        reportDTO.setDescription(report.getDescription());
        reportDTO.setCitizen(convertCitizenToDTO(report.getCitizen()));
        reportDTO.setIssue(convertIssueToDTO(report.getIssue()));
        reportDTO.setStatus(report.getStatus());
        reportDTO.setCoordinates(convertCoordinatesToDTO(report.getCoordinates()));
        reportDTO.setCreateDate(report.getCreateDate());
        reportDTO.setUpdateDate(report.getUpdateDate());
        reportDTO.setFotoUrl(report.getFotoUrl());
        return reportDTO;
    }
    // Método que convierte un ciudadano a un DTO
    private CitizenDTO convertCitizenToDTO(Citizen citizen) {
        if (citizen == null) {
            return null;
        }
        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setId(citizen.getId());
        citizenDTO.setName(citizen.getName());
        citizenDTO.setEmail(citizen.getEmail());
        citizenDTO.setPhone(citizen.getPhone());
        return citizenDTO;
    }
    // Método que convierte un Issuea a un DTO
    private IssueDTO convertIssueToDTO(Issue issue) {
        if (issue == null) {
            return null; 
        }
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(issue.getId());
        issueDTO.setCategory(issue.getCategory());
        issueDTO.setPriority(issue.getPriority());
        return issueDTO;
    }
    // Método que convierte unas coordenadas a un DTO
    private CoordinatesDTO convertCoordinatesToDTO(Coordinates coordinates) {
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setLatitude(coordinates.getLatitude());
        coordinatesDTO.setLongitude(coordinates.getLongitude());
        return coordinatesDTO;
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
