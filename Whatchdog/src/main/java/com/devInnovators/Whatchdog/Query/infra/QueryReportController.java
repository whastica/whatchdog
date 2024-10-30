package com.devInnovators.Whatchdog.Query.infra;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Query.application.interfaces.QueryReportServiceInterface;


@RestController
@RequestMapping("/api/reports")
public class QueryReportController {

    @Autowired
    private QueryReportServiceInterface queryReportService;

    @GetMapping
    public List<ReportDTO> getAllReports() {
        return queryReportService.getAllReports();
    }

    @GetMapping("/{id}")
    public ReportDTO getReportById(@PathVariable String id) {
        return queryReportService.getReportById(id);
    }

    @GetMapping("/citizen/{citizenId}")
    public List<ReportDTO> getReportsByCitizenId(@PathVariable String citizenId) {
        return queryReportService.getReportsByCitizenId(citizenId);
    }

    @GetMapping("/issue/{issueId}")
    public List<ReportDTO> getReportsByIssueId(@PathVariable String issueId) {
        return queryReportService.getReportsByIssueId(issueId);
    }
}