package com.devInnovators.Whatchdog.Command.aplicattion.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.CitizenDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.DTO.CoordinatesDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.DTO.IssueDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.interfaces.CommandReportServiceInterface;
import com.devInnovators.Whatchdog.Command.domain.model.Citizen;
import com.devInnovators.Whatchdog.Command.domain.model.Coordinates;
import com.devInnovators.Whatchdog.Command.domain.model.Issue;
import com.devInnovators.Whatchdog.Command.domain.model.Report;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandCitizenRepository;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandIssueRepository;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandReportRepository;
import com.devInnovators.Whatchdog.Command.exception.ResourceNotFoundException;

@Service
public class CommandReportServiceImpl implements CommandReportServiceInterface {
    
    private final CommandReportRepository reportRepository;
    private final CommandCitizenRepository citizenRepository;  // Asegúrate de tener un repositorio para Citizen
    private final CommandIssueRepository issueRepository; // Asegúrate de tener un repositorio para Problem

    public CommandReportServiceImpl(CommandReportRepository reportRepository, CommandCitizenRepository citizenRepository, CommandIssueRepository issueRepository) {
        this.reportRepository = reportRepository;
        this.citizenRepository = citizenRepository;
        this.issueRepository = issueRepository;
    }

    // Método para crear un nuevo reporte
    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        // Cargar las entidades de Citizen y Problem
        Citizen citizen = citizenRepository.findById(reportDTO.getCitizen().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Ciudadano no encontrado con id: " + reportDTO.getCitizen().getId()));
        Issue issue = issueRepository.findById(reportDTO.getIssue().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Problema no encontrado con id: " + reportDTO.getIssue().getId()));

        // Convertir DTO a entidad
        Report report = new Report();
        report.setId(reportDTO.getId());
        report.setDescription(reportDTO.getDescription());
        report.setCitizen(citizen);  // Establecer la entidad Citizen
        report.setIssue(issue);    // Establecer la entidad Problem
        report.setStatus(reportDTO.getStatus());
        report.setCoordinates(new Coordinates(reportDTO.getCoordinates().getLatitude(), reportDTO.getCoordinates().getLongitude()));
        report.setCreateDate(LocalDateTime.now()); // Establecer la fecha de creación
        report.setUpdateDate(LocalDateTime.now()); // Establecer la fecha de actualización
        report.setFotoUrl(reportDTO.getFotoUrl());

        // Guardar el reporte en la base de datos
        Report savedReport = reportRepository.save(report);

        // Convertir entidad de vuelta a DTO
        return convertToDTO(savedReport);
    }

    // Método para actualizar un reporte existente
    @Override
    public ReportDTO updateReport(String id, ReportDTO reportDTO) {
        // Buscar el reporte existente
        Report existingReport = reportRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));

        // Cargar las entidades de Citizen y Problem
        Citizen citizen = citizenRepository.findById(reportDTO.getCitizen().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Ciudadano no encontrado con id: " + reportDTO.getCitizen().getId()));
        Issue issue = issueRepository.findById(reportDTO.getIssue().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Problema no encontrado con id: " + reportDTO.getIssue().getId()));

        // Actualizar los campos del reporte
        existingReport.setDescription(reportDTO.getDescription());
        existingReport.setCitizen(citizen);  // Establecer la entidad Citizen
        existingReport.setIssue(issue);    // Establecer la entidad Problem
        existingReport.setStatus(reportDTO.getStatus());
        existingReport.setCoordinates(new Coordinates(reportDTO.getCoordinates().getLatitude(), reportDTO.getCoordinates().getLongitude()));
        existingReport.setUpdateDate(LocalDateTime.now()); // Actualizar la fecha de actualización
        existingReport.setFotoUrl(reportDTO.getFotoUrl());

        // Guardar el reporte actualizado en la base de datos
        Report updatedReport = reportRepository.save(existingReport);

        // Convertir entidad de vuelta a DTO
        return convertToDTO(updatedReport);
    }

    // Método para eliminar un reporte
    @Override
    public void deleteReport(String id) {
        // Verificar si el reporte existe
        if (!reportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reporte no encontrado con id: " + id);
        }
        // Eliminar el reporte
        reportRepository.deleteById(id);
    }

    // Método para convertir Report a ReportDTO
    private ReportDTO convertToDTO(Report report) {
        return new ReportDTO(
            report.getId(),
            report.getDescription(),
            new CitizenDTO(report.getCitizen().getId(), report.getCitizen().getName(), report.getCitizen().getEmail(), report.getCitizen().getPhone()),
            new IssueDTO(report.getIssue().getId(), report.getIssue().getCategory(), report.getIssue().getPriority()),
            report.getStatus(),
            new CoordinatesDTO(report.getCoordinates().getLatitude(), report.getCoordinates().getLongitude()),
            report.getCreateDate(),
            report.getUpdateDate(),
            report.getFotoUrl()
        );
    }
}