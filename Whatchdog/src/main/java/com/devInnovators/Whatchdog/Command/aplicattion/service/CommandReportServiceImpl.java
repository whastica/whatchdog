package com.devInnovators.Whatchdog.Command.aplicattion.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.interfaces.CommandReportServiceInterface;
import com.devInnovators.Whatchdog.Command.domain.model.AdminC;
import com.devInnovators.Whatchdog.Command.domain.model.Citizen;
import com.devInnovators.Whatchdog.Command.domain.model.Comment;
import com.devInnovators.Whatchdog.Command.domain.model.Coordinates;
import com.devInnovators.Whatchdog.Command.domain.model.Issue;
import com.devInnovators.Whatchdog.Command.domain.model.Report;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandAdminCRepository;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandCitizenRepository;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandCommentRepository;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandIssueRepository;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandReportRepository;
import com.devInnovators.Whatchdog.Command.exception.ResourceNotFoundException;
import com.devInnovators.Whatchdog.Sync.serviceSync.SyncService;
import com.devInnovators.Whatchdog.Command.aplicattion.DTO.CommentDTO;

@Service
public class CommandReportServiceImpl implements CommandReportServiceInterface {
    
    private final CommandReportRepository reportRepository;
    private final CommandCitizenRepository citizenRepository;  // Asegúrate de tener un repositorio para Citizen
    private final CommandIssueRepository issueRepository; // Asegúrate de tener un repositorio para Problem
    private final CommandAdminCRepository adminCRepository;
    private final CommandCommentRepository commentRepository;
    
    @Autowired
    private SyncService syncService;

    public CommandReportServiceImpl(CommandReportRepository reportRepository, CommandCitizenRepository citizenRepository,CommandIssueRepository issueRepository, CommandAdminCRepository admincRepository, CommandCommentRepository commentRepository) {
        this.reportRepository = reportRepository;
        this.citizenRepository = citizenRepository;
        this.issueRepository = issueRepository;
        this.adminCRepository= admincRepository;
        this.commentRepository= commentRepository;
    }


    // Método para crear un nuevo reporte
    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        // Cargar las entidades de Citizen y Problem
        Citizen citizen = citizenRepository.findById(reportDTO.getIdCitizen())
            .orElseThrow(() -> new ResourceNotFoundException("Ciudadano no encontrado con id: " + reportDTO.getIdCitizen()));
        Issue issue = issueRepository.findById(reportDTO.getIdissue())
            .orElseThrow(() -> new ResourceNotFoundException("Problema no encontrado con id: " + reportDTO.getIdissue()));

        AdminC adminc = adminCRepository.findById(reportDTO.getIdAdminC())
            .orElseThrow(() -> new ResourceNotFoundException("Ciudadano no encontrado con id: " + reportDTO.getIdAdminC()));
            
        // Convertir DTO a entidad
        Report report = new Report();
        report.setIdReport(reportDTO.getIdReport());
        report.setDescription(reportDTO.getDescription());
        report.setCitizen(citizen);  // Establecer la entidad Citizen
        report.setIssue(issue);  
        report.setAdminC(adminc); 
        report.setStatus(reportDTO.getStatus());
        report.setCategoryIssue(reportDTO.getCategoryIssue());
        report.setCoordinates(new Coordinates(reportDTO.getCoordinates().getLatitude(), reportDTO.getCoordinates().getLongitude()));
        report.setCreateDate(LocalDateTime.now()); // Establecer la fecha de creación
        report.setUpdateDate(LocalDateTime.now()); // Establecer la fecha de actualización
        report.setFotoUrl(reportDTO.getFotoUrl());
        report.setNumLikes(reportDTO.getNumLikes());
        report.setNumDislikes(reportDTO.getNumDislikes());

        if (reportDTO.getComment() != null && !reportDTO.getComment().isEmpty()) {
            List<Comment> comments = new ArrayList<>();
            for (CommentDTO commentDTO : reportDTO.getComment()) {
                Comment comment = new Comment();
                comment.setId(commentDTO.getId());
                comment.setDescription(commentDTO.getDescription());
                comment.setCitizenId(citizen);
                comment.setReport(report);  // Asociamos el comentario con el reporte
                comment.setCreateDate(commentDTO.getCreateDate()); // Fecha de creación del comentario
                comments.add(comment);
            }
            report.setComments(comments);  // Asociamos los comentarios al reporte
        }
        // Guardar el reporte en la base de datos
        Report savedReport = reportRepository.save(report);
        try {
    syncService.syncAllReports();
} catch (Exception e) {
    // Manejar el error o registrar detalles de la excepción
    System.err.println("Error durante la sincronización: " + e.getMessage());
}

        // Convertir entidad de vuelta a DTO
        return convertToDTO(savedReport);
    }

    // Método para actualizar un reporte existente
    @Override
    public ReportDTO updateReport(String idReport, ReportDTO reportDTO) {
        // Buscar el reporte existente
        Report existingReport = reportRepository.findById(idReport)
            .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + idReport));

        // Cargar las entidades de Citizen y Problem
        Citizen citizen = citizenRepository.findById(reportDTO.getIdCitizen())
            .orElseThrow(() -> new ResourceNotFoundException("Ciudadano no encontrado con id: " + reportDTO.getIdCitizen()));
        Issue issue = issueRepository.findById(reportDTO.getIdissue())
            .orElseThrow(() -> new ResourceNotFoundException("Problema no encontrado con id: " + reportDTO.getIdissue()));

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

        syncService.syncReportById(updatedReport.getIdReport());

        // Convertir entidad de vuelta a DTO
        return convertToDTO(updatedReport);
    }

    // Método para eliminar un reporte
    @Override
    public void deleteReport(String idReport) {
        // Verificar si el reporte existe
        if (!reportRepository.existsById(idReport)) {
            throw new ResourceNotFoundException("Reporte no encontrado con id: " + idReport);
        }
        // Eliminar el reporte
        reportRepository.deleteById(idReport);
        syncService.deleteReportFromMongoById(idReport);
    }

    // Método para convertir Report a ReportDTO
    private ReportDTO convertToDTO(Report report) {
        ReportDTO dto = new ReportDTO();
        dto.setIdReport(report.getIdReport());
        dto.setDescription(report.getDescription());
        dto.setIdCitizen(report.getCitizen() != null ? report.getCitizen().getId() : null);
        dto.setIdissue(report.getIssue() != null ? report.getIssue().getId() : null);
        dto.setIdAdminC(report.getAdminC() != null ? report.getAdminC().getId() : null);
        dto.setStatus(report.getStatus());
        dto.setCategoryIssue(report.getCategoryIssue());
        dto.setCoordinates(report.getCoordinates());
        dto.setCreateDate(report.getCreateDate());
        dto.setUpdateDate(report.getUpdateDate());
        dto.setFotoUrl(report.getFotoUrl());
        dto.setNumLikes(report.getNumLikes());
        dto.setNumDislikes(report.getNumDislikes());
    
        // Asigna IDs de entidades relacionadas
       
       
       
    
        // Convierte los comentarios a DTOs
        dto.setComment(report.getComments().stream()
        .map(comment -> new CommentDTO(comment.getId(), comment.getDescription(), comment.getCitizenId().getId(), comment.getCreateDate(), comment.getReport().getIdReport()))
        .collect(Collectors.toList()));
        
        return dto;
    }

    // Método para crear los comentarios a reportes
    
   /*  public void createCommentToReport(String reportId) {
        // Buscar el reporte al que se le añadirá el comentario
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + reportId));

        // Crear un nuevo comentario
        Comment comment = new Comment();
        comment.setDescription("Nuevo comentario");
        comment.setCitizenId(report.getCitizen());  // Asignar el ciudadano del reporte al comentario
        comment.setReport(report);  // Asignar el reporte al comentario
        comment.setCreateDate(LocalDateTime.now());  // Establecer la fecha de creación

        // Guardar el comentario en la base de datos
        commentRepository.save(comment);
    
      
    } */
    @Override
    public CommentDTO createCommentToReport(CommentDTO commentDTO, String reportId) {
        // Buscar el reporte al que se le añadirá el comentario
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + reportId));

        // Crear un nuevo comentario
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setDescription(commentDTO.getDescription());
        comment.setCitizenId(report.getCitizen());  // Asignar el ciudadano del reporte al comentario
        comment.setReport(report);  // Asignar el reporte al comentario
        comment.setCreateDate(LocalDateTime.now());  // Establecer la fecha de creación

        // Guardar el comentario en la base de datos
        Comment savedComment = commentRepository.save(comment);

        // Convertir entidad de vuelta a DTO
        return convertToDTO(savedComment);
        
    }

    //Convertir Comment a CommentDTO
    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setDescription(comment.getDescription());
        dto.setCitizenId(comment.getCitizenId().getId());
        dto.setCreateDate(comment.getCreateDate());
        dto.setReportId(comment.getReport().getIdReport());
        return dto;
    }

    // Método para agregar un comentario
    @Override
    public void addCommentToReport(String reportId, String commentId) {
        // Buscar el reporte al que se le añadirá el comentario
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + reportId));

        // Buscar el comentario que se añadirá al reporte
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado con id: " + commentId));

        // Asignar el comentario al reporte
        report.getComments().add(comment);

        // Guardar el reporte actualizado en la base de datos
        reportRepository.save(report);
    }


}