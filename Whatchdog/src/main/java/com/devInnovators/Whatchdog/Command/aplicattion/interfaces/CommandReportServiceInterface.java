package com.devInnovators.Whatchdog.Command.aplicattion.interfaces;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;

public interface CommandReportServiceInterface {

    ReportDTO createReport(ReportDTO reportDTO);

    ReportDTO updateReport(String id, ReportDTO reportDTO);
        
    void deleteReport(String id);
}
