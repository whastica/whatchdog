package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.aplication.interfaces;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.aplication.DTO.ReportDTO;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.aplication.DTO.CommentDTO;

import java.util.List;

public interface CommandReviewServiceInterface {

    // Crear un nuevo reporte
    ReportDTO createReport(ReportDTO reportDTO);

    // Cambiar estado del reporte a "en revision"
    ReportDTO changeStatusToReview(String reportId);

    // Asignar una categoría de Issue al reporte
    ReportDTO assignCategoryToReport(String reportId, String categoryIssue);

    // Actualizar un reporte existente
    ReportDTO updateReport(String reportId, ReportDTO reportDTO);

    // Asignar un reporte a un Issue
    ReportDTO assignReportToIssue(String reportId, String issueId);

    // Obtener todos los reportes asignados a un Issue específico
    List<ReportDTO> getReportsByIssue(String issueId);

    // Agregar un comentario a un reporte
    CommentDTO addCommentToReport(String reportId, CommentDTO commentDTO);
}
