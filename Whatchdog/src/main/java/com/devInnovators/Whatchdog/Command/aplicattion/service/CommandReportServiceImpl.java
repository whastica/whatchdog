package com.devInnovators.Whatchdog.Command.aplicattion.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.interfaces.CommandReportServiceInterface;
import com.devInnovators.Whatchdog.Command.domain.model.Citizen;
import com.devInnovators.Whatchdog.Command.domain.model.Comment;
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
        Citizen citizen = citizenRepository.findById(reportDTO.getCitizenId())
            .orElseThrow(() -> new ResourceNotFoundException("Ciudadano no encontrado con id: " + reportDTO.getCitizenId()));
        Issue issue = issueRepository.findById(reportDTO.getIssueId())
            .orElseThrow(() -> new ResourceNotFoundException("Problema no encontrado con id: " + reportDTO.getIssueId()));

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
        Citizen citizen = citizenRepository.findById(reportDTO.getCitizenId())
            .orElseThrow(() -> new ResourceNotFoundException("Ciudadano no encontrado con id: " + reportDTO.getCitizenId()));
        Issue issue = issueRepository.findById(reportDTO.getIssueId())
            .orElseThrow(() -> new ResourceNotFoundException("Problema no encontrado con id: " + reportDTO.getIssueId()));

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
        ReportDTO dto = new ReportDTO();
        dto.setId(report.getId());
        dto.setDescription(report.getDescription());
        dto.setStatus(report.getStatus());
        dto.setCategoryIssue(report.getCategoryIssue());
        dto.setCoordinates(report.getCoordinates());
        dto.setCreateDate(report.getCreateDate());
        dto.setUpdateDate(report.getUpdateDate());
        dto.setFotoUrl(report.getFotoUrl());
        dto.setNumLikes(report.getNumLikes());
        dto.setNumDislikes(report.getNumDislikes());
    
        // Asigna IDs de entidades relacionadas
        dto.setCitizenId(report.getCitizen() != null ? report.getCitizen().getId() : null);
        dto.setIssueId(report.getIssue() != null ? report.getIssue().getId() : null);
        dto.setAdmincId(report.getAdminC() != null ? report.getAdminC().getId() : null);
    
        // Convierte lista de comentarios a lista de IDs
        dto.setCommentIds(report.getComments().stream()
                               .map(Comment::getId)
                               .collect(Collectors.toList()));
    
        return dto;
    }
}