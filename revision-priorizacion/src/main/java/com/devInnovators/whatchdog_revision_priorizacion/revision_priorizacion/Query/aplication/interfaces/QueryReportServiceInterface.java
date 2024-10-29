package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.interfaces;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.DTO.ReportDTO;
import java.util.List;

public interface QueryReportServiceInterface {

    List<ReportDTO> getAllReports();
    
    ReportDTO getReportById(String reportId);
    
    List<ReportDTO> getReportsByIssueId(String issueId);
}
