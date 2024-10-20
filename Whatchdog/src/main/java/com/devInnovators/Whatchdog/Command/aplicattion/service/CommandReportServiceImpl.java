package com.devInnovators.Whatchdog.Command.aplicattion.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.interfaces.CommandReportServiceInterface;
import com.devInnovators.Whatchdog.Command.domain.model.Report;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandReportRepository;
import com.devInnovators.Whatchdog.Command.exception.ResourceNotFoundException;

@Service
public class CommandReportServiceImpl implements CommandReportServiceInterface {
    
     private final CommandReportRepository reportRepository;

    public CommandReportServiceImpl(CommandReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // Método para crear un nuevo reporte
    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        // Convertir DTO a entidad
        Report report = new Report();
        report.setId(reportDTO.getId());
        report.setDescription(reportDTO.getDescription());
        report.setCitizen(reportDTO.getCitizen());
        report.setProblem(reportDTO.getProblem());
        report.setStatus(reportDTO.getStatus());
        report.setCoordinates(reportDTO.getCoordinates());
        report.setCreateDate(LocalDateTime.now()); // Establecer la fecha de creación
        report.setUpdateDate(LocalDateTime.now()); // Establecer la fecha de actualización
        report.setFotoUrl(reportDTO.getFotoUrl());

        // Guardar el reporte en la base de datos
        Report savedReport = reportRepository.save(report);

        // Convertir entidad de vuelta a DTO
        return new ReportDTO(
            savedReport.getId(),
            savedReport.getDescription(),
            savedReport.getCitizen(),
            savedReport.getProblem(),
            savedReport.getStatus(),
            savedReport.getCoordinates(),
            savedReport.getCreateDate(),
            savedReport.getUpdateDate(),
            savedReport.getFotoUrl()
        );
    }

    // Método para actualizar un reporte existente
    @Override
    public ReportDTO updateReport(String id, ReportDTO reportDTO) {
        // Buscar el reporte existente
        Report existingReport = reportRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));

        // Actualizar los campos del reporte
        existingReport.setDescription(reportDTO.getDescription());
        existingReport.setCitizen(reportDTO.getCitizen());
        existingReport.setProblem(reportDTO.getProblem());
        existingReport.setStatus(reportDTO.getStatus());
        existingReport.setCoordinates(reportDTO.getCoordinates());
        existingReport.setUpdateDate(LocalDateTime.now()); // Actualizar la fecha de actualización
        existingReport.setFotoUrl(reportDTO.getFotoUrl());

        // Guardar el reporte actualizado en la base de datos
        Report updatedReport = reportRepository.save(existingReport);

        // Convertir entidad de vuelta a DTO
        return new ReportDTO(
            updatedReport.getId(),
            updatedReport.getDescription(),
            updatedReport.getCitizen(),
            updatedReport.getProblem(),
            updatedReport.getStatus(),
            updatedReport.getCoordinates(),
            updatedReport.getCreateDate(),
            updatedReport.getUpdateDate(),
            updatedReport.getFotoUrl()
        );
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

}
