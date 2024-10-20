package com.devInnovators.Whatchdog.Command.aplicattion.interfaces;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;

public interface CommandReportServiceInterface {

    ReportDTO createReport(ReportDTO report);

    ReportDTO updateReport(String id, ReportDTO report);
        
    void deleteReport(String id);
}
