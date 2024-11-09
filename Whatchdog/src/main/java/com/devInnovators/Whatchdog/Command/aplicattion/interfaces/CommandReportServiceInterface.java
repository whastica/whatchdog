package com.devInnovators.Whatchdog.Command.aplicattion.interfaces;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.CommentDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;

public interface CommandReportServiceInterface {

    ReportDTO createReport(ReportDTO reportDTO);

    ReportDTO updateReport(String idReport, ReportDTO reportDTO);
        
    void deleteReport(String idReport);

    CommentDTO createCommentToReport(CommentDTO commentDTO, String idReport);

    void addCommentToReport(String idReport, String idComment);

}
