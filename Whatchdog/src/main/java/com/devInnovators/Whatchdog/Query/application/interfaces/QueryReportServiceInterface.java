package com.devInnovators.Whatchdog.Query.application.interfaces;


import java.util.List;

import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;

public interface QueryReportServiceInterface {

    List<ReportDTO> getAllReports(); 
    ReportDTO getReportById(String id); 
    List<ReportDTO> getReportsByCitizenId(String citizenId); 
    List<ReportDTO> getReportsByIssueId(String issueId);

}
