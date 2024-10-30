package com.devInnovators.Whatchdog.Query.application.interfaces;


import com.devInnovators.Whatchdog.Query.domain.model.Status;

import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;

import java.util.List;

public interface QueryReportServiceInterface {

    ReportDTO findReportById(String id);
    List<ReportDTO> findAllReports();
    List<ReportDTO> findReportsByStatus(Status status); 
    List<ReportDTO> getReportsByCategoryIssue(String categoryIssue);
    List<ReportDTO> getReportsByAdminId(String adminId);  

  

}
