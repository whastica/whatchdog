package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.service;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.DTO.ReportDTO;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.aplication.interfaces.QueryReportServiceInterface;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model.QueryReport;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.repository.QueryReportRepository;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryReportServiceImpl implements QueryReportServiceInterface {

    @Autowired
    private QueryReportRepository reportRepository;

    @Override
    public List<ReportDTO> getAllReports() {
        return reportRepository.findAll().stream()
            .map(ReportDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public ReportDTO getReportById(String reportId) {
        QueryReport report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ResourceNotFoundException("Report not found with id " + reportId));
        return new ReportDTO(report);
    }

    @Override
    public List<ReportDTO> getReportsByIssueId(String issueId) {
        return reportRepository.findByIssueId(issueId).stream()
            .map(ReportDTO::new)
            .collect(Collectors.toList());
    }
}
