package com.devInnovators.Whatchdog.Query.application.interfaces;


import com.devInnovators.Whatchdog.Query.domain.model.QueryStatus;

import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;

import java.util.List;

public interface QueryReportServiceInterface {

    ReportDTO findByIdReport(String idReport);
    List<ReportDTO> findAllReports();
    List<ReportDTO> findReportsByStatus(QueryStatus status); 
    List<ReportDTO> getReportsByCategoryIssue(String categoryIssue);
    List<ReportDTO> getReportsByAdminId(String adminId);  

  

}
