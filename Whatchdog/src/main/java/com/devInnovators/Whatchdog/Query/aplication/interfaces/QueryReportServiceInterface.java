package com.devInnovators.Whatchdog.Query.aplication.interfaces;

import com.devInnovators.Whatchdog.Query.aplication.DTO.ReportDTO;
import java.util.List;

public interface QueryReportServiceInterface {

    List<ReportDTO> getAllReports();
    
    ReportDTO getReportById(String reportId);
    
    List<ReportDTO> getReportsByIssueId(String issueId);
}
