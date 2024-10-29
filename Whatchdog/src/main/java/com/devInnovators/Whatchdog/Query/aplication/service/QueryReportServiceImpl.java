package com.devInnovators.Whatchdog.Query.aplication.service;

import com.devInnovators.Whatchdog.Query.aplication.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Query.aplication.interfaces.QueryReportServiceInterface;
import com.devInnovators.Whatchdog.Query.domain.model.QueryReport;
import com.devInnovators.Whatchdog.Query.domain.repository.QueryReportRepository;
import com.devInnovators.Whatchdog.Command.exception.ResourceNotFoundException;

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
