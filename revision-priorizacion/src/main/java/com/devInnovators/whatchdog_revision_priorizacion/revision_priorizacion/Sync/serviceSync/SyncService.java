package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Sync.serviceSync;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.Issue;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.Report;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model.QueryIssue;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.model.QueryReport;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.repository.QueryIssueRepository;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Query.domain.repository.QueryReportRepository;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.repository.CommandIssueRepository;
import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.repository.CommandReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SyncService {

    @Autowired
    private CommandIssueRepository commandIssueRepository;

    @Autowired
    private CommandReportRepository commandReportRepository;

    @Autowired
    private QueryIssueRepository queryIssueRepository;

    @Autowired
    private QueryReportRepository queryReportRepository;

    @Transactional
    public void syncIssue(String issueId) {
        Issue issue = commandIssueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found with id " + issueId));
        
        // Convertimos el Issue de Command a QueryIssue
        QueryIssue queryIssue = new QueryIssue(issue);
        queryIssueRepository.save(queryIssue); // Guardamos el QueryIssue en la base de datos de consulta
    }

    @Transactional
    public void syncReport(String reportId) {
        Report report = commandReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id " + reportId));
        
        // Convertimos el Report de Command a QueryReport
        QueryReport queryReport = new QueryReport(report);
        queryReportRepository.save(queryReport); // Guardamos el QueryReport en la base de datos de consulta
    }
}
