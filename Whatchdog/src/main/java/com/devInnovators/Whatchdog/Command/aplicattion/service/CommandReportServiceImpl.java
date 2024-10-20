package com.devInnovators.Whatchdog.Command.aplicattion.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.interfaces.CommandReportServiceInterface;
import com.devInnovators.Whatchdog.Command.domain.model.Report;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandReportRepository;

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
        //report.setCitizenId(reportDTO.getCitizen().getId()); // Cambiado para usar citizen
        //report.setCoordinates(reportDTO.getCoordinates());
        report.setStatus(reportDTO.getStatus()); // Asegúrate de que esto tenga sentido
        report.setCreateDate(reportDTO.getCreateDate()); // Cambiado a createDate

        // Guardar el reporte en la base de datos
        Report savedReport = reportRepository.save(report);

        // Convertir entidad de vuelta a DTO
        return new ReportDTO(
                savedReport.getId(),
                savedReport.getDescription(),
                savedReport.getCitizen(), // Cambiado a citizen
                savedReport.getProblem(), // Asegúrate de que esto esté en tu entidad Report
                savedReport.getStatus(), // Asegúrate de que esto esté en tu entidad Report
                savedReport.getCoordinates(),
                savedReport.getCreateDate(), // Cambiado a createDate
                savedReport.getUpdateDate(), // Asegúrate de que esto esté en tu entidad Report
                savedReport.getFotoUrl() // Asegúrate de que esto esté en tu entidad Report
        );
    }

    @Override
    public ReportDTO updateReport(String id, ReportDTO reportDTO) {
        // Buscar el reporte por su ID
        Optional<Report> reportOpt = reportRepository.findById(id);
        
        if (reportOpt.isPresent()) {
            Report report = reportOpt.get();
            // Actualizar los valores del reporte
            report.setId(reportDTO.getId());
            report.setDescription(reportDTO.getDescription());
            //report.setCitizenId(reportDTO.getCitizen().getId()); // Cambiado para usar citizen
            //report.setCoordinates(reportDTO.getCoordinates());
            report.setStatus(reportDTO.getStatus()); // Asegúrate de que esto tenga sentido
            report.setCreateDate(reportDTO.getCreateDate()); // Cambiado a createDate

            // Guardar cambios
            Report updatedReport = reportRepository.save(report);

            // Convertir a DTO
            return new ReportDTO(
                    updatedReport.getId(),
                    updatedReport.getDescription(),
                    updatedReport.getCitizen(), // Cambiado a citizen
                    updatedReport.getProblem(), // Asegúrate de que esto esté en tu entidad Report
                    updatedReport.getStatus(), // Asegúrate de que esto esté en tu entidad Report
                    updatedReport.getCoordinates(),
                    updatedReport.getCreateDate(), // Cambiado a createDate
                    updatedReport.getUpdateDate(), // Asegúrate de que esto esté en tu entidad Report
                    updatedReport.getFotoUrl() // Asegúrate de que esto esté en tu entidad Report
            );
        } else {
            throw new RuntimeException("Reporte no encontrado con el id: " + id);
        }
    }

    @Override
    public void deleteReport(String id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se pudo eliminar. Reporte no encontrado con el id: " + id);
        }
    }

}
