package com.devInnovators.Whatchdog.Command.aplicattion.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.CommentDTO;
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

@Service
public class CommandReportServiceImpl implements CommandReportServiceInterface {
    
    private final CommandReportRepository reportRepository;
    private final CommandCitizenRepository citizenRepository;  // Asegúrate de tener un repositorio para Citizen
    private final CommandIssueRepository issueRepository; // Asegúrate de tener un repositorio para Problem
    private final CommandAdminCRepository adminCRepository;
    private final CommandCommentRepository commentRepository;

    private final SyncService syncService;

    public CommandReportServiceImpl(CommandReportRepository reportRepository, CommandCitizenRepository citizenRepository,CommandIssueRepository issueRepository, CommandAdminCRepository admincRepository, CommandCommentRepository commentRepository,SyncService syncService) {
        this.reportRepository = reportRepository;
        this.citizenRepository = citizenRepository;
        this.issueRepository = issueRepository;
        this.adminCRepository= admincRepository;
        this.commentRepository= commentRepository;
        this.syncService = syncService;
    }


    // Método para crear un nuevo reporte
    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {

        if (reportDTO.get_id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idReport is required");
        }

        // Imprimir el idReport para asegurarse de que se está recibiendo correctamente
        System.out.println("Received idReport: " + reportDTO.get_id());
        // Crear la entidad Report y mapear los valores del DTO
        Report report = new Report();
        if (reportDTO.get_id() != null) {
            report.set_id(reportDTO.get_id());
        }
        report.setDescription(reportDTO.getDescription());

        // Cargar el Citizen si el ID es válido, o lanzar excepción si no se encuentra
        System.out.println("idCitizen: " + reportDTO.getIdCitizen());
        Citizen citizen = citizenRepository.findById(reportDTO.getIdCitizen())
            .orElseThrow(() -> new ResourceNotFoundException("Ciudadano no encontrado con id: " + reportDTO.getIdCitizen()));
        report.setCitizen(citizen);

        // Cargar Issue solo si el idissue no es null
        if (reportDTO.getIdissue() != null) {
            Issue issue = issueRepository.findById(reportDTO.getIdissue())
                .orElseThrow(() -> new ResourceNotFoundException("Problema no encontrado con id: " + reportDTO.getIdissue()));
            report.setIssue(issue);
        } else {
            report.setIssue(null);  // Si idissue es null, no asignamos ningún Issue
        }

        // Cargar AdminC si está presente en el DTO, de lo contrario dejar como null
        System.out.println("idAdminC: " + reportDTO.getIdAdminC());
        if (reportDTO.getIdAdminC() != null) {
            AdminC adminC = adminCRepository.findById(reportDTO.getIdAdminC())
                .orElseThrow(() -> new ResourceNotFoundException("AdminC no encontrado con id: " + reportDTO.getIdAdminC()));
            report.setAdminC(adminC);
        } else {
            report.setAdminC(null);  // Permitir que adminC sea null si no se proporciona
        }

        // Configurar el estado y la categoría de problemas
        report.setStatus(reportDTO.getStatus());
        report.setCategoryIssue(reportDTO.getCategoryIssue());

        // Configurar coordenadas si están presentes en el DTO
        if (reportDTO.getCoordinates() != null) {
            report.setCoordinates(new Coordinates(reportDTO.getCoordinates().getLatitude(), reportDTO.getCoordinates().getLongitude()));
        }

        // Configurar fechas, foto y conteo de likes y dislikes
        report.setCreateDate(LocalDateTime.now()); // Fecha de creación actual
        report.setUpdateDate(LocalDateTime.now()); // Fecha de actualización actual
        report.setFotoUrl(reportDTO.getFotoUrl());
        report.setNumLikes(reportDTO.getNumLikes());
        report.setNumDislikes(reportDTO.getNumDislikes());

        // No asignar comentarios en el momento de la creación del reporte
        report.setComments(new ArrayList<>()); // No asignar comentarios si el reporte es recién creado

        // Guardar el reporte en la base de datos
        Report savedReport = reportRepository.save(report);

        // Intentar sincronización y manejar excepciones
        try {
            syncService.syncAllReports();
        } catch (Exception e) {
            // Manejar el error o registrar detalles de la excepción
            System.err.println("Error durante la sincronización: " + e.getMessage());
        }

        // Convertir la entidad guardada a DTO y retornarla
        return convertToDTO(savedReport);
    }

    @Override
    public ReportDTO updateReport(String _id, ReportDTO reportDTO) {
        System.out.println("Received idReport: " + _id);

        // Buscar el reporte existente
        Report existingReport = reportRepository.findById(_id)
            .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + _id));

        // Actualizar los campos del reporte solo si se pasan en el DTO
        if (reportDTO.getDescription() != null) {
            existingReport.setDescription(reportDTO.getDescription());
        }
        if (reportDTO.getIdCitizen() != null) {
            Citizen citizen = citizenRepository.findById(reportDTO.getIdCitizen())
                .orElseThrow(() -> new ResourceNotFoundException("Ciudadano no encontrado con id: " + reportDTO.getIdCitizen()));
            existingReport.setCitizen(citizen);
        }

        // Cargar Issue solo si el idissue no es null
        if (reportDTO.getIdissue() != null) {
            Issue issue = issueRepository.findById(reportDTO.getIdissue())
                .orElseThrow(() -> new ResourceNotFoundException("Problema no encontrado con id: " + reportDTO.getIdissue()));
            existingReport.setIssue(issue);
        } else {
            existingReport.setIssue(null);  // Si idissue es null, no asignamos ningún Issue
        }

        // Solo actualizamos el status y otros campos que se pasan desde el evento
        if (reportDTO.getStatus() != null) {
            existingReport.setStatus(reportDTO.getStatus());
        }
        if (reportDTO.getCoordinates() != null) {
            existingReport.setCoordinates(reportDTO.getCoordinates());
        }

        // Fecha de actualización
        existingReport.setUpdateDate(LocalDateTime.now());

        // Foto URL solo si se pasa en el DTO
        if (reportDTO.getFotoUrl() != null) {
            existingReport.setFotoUrl(reportDTO.getFotoUrl());
        }

        if (reportDTO.getCategoryIssue() != null) {
            existingReport.setCategoryIssue(reportDTO.getCategoryIssue());
        }

        // Guardar el reporte actualizado en la base de datos
        Report updatedReport = reportRepository.save(existingReport);

        // Sincronizar el reporte con el servicio de sincronización
        syncService.syncReportById(_id);

        // Convertir entidad de vuelta a DTO y retornar
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
        dto.set_id(report.get_id());
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
        .map(comment -> new CommentDTO(comment.getId(), comment.getDescription(), comment.getCitizenId().getId(), comment.getCreateDate(), comment.getReport().get_id()))
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
        dto.setReportId(comment.getReport().get_id());
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