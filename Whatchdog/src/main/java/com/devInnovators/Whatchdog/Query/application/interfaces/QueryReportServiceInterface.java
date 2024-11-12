package com.devInnovators.Whatchdog.Query.application.interfaces;


import com.devInnovators.Whatchdog.Query.domain.model.QueryStatus;
import java.util.List;

import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Query.application.EventsDTO.RevisedReportEvent;

public interface QueryReportServiceInterface {

    ReportDTO findByIdReport(String idReport);
    List<ReportDTO> findAllReports();
    List<ReportDTO> findReportsByStatus(QueryStatus status); 
    List<ReportDTO> getReportsByCategoryIssue(String categoryIssue);
    List<ReportDTO> getReportsByAdminId(String adminId);  
    void updateReportStatus(RevisedReportEvent revisedReportEvent);

  

}
