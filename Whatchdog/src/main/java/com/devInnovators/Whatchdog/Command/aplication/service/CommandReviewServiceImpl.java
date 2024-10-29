package com.devInnovators.Whatchdog.Command.aplication.service;

import com.devInnovators.Whatchdog.Command.aplication.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Command.aplication.DTO.CommentDTO;
import com.devInnovators.Whatchdog.Command.aplication.interfaces.CommandReviewServiceInterface;
import com.devInnovators.Whatchdog.Command.domain.model.Report;
import com.devInnovators.Whatchdog.Command.domain.model.CategoryIssue;
import com.devInnovators.Whatchdog.Command.domain.model.Status;
import com.devInnovators.Whatchdog.Command.domain.model.Comment;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandReportRepository;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandIssueRepository;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandCommentRepository;
import com.devInnovators.Whatchdog.Command.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandReviewServiceImpl implements CommandReviewServiceInterface {

    @Autowired
    private CommandReportRepository reportRepository;

    @Autowired
    private CommandIssueRepository issueRepository;

    @Autowired
    private CommandCommentRepository commentRepository;

    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        Report report = new Report(reportDTO);
        report = reportRepository.save(report);
        return new ReportDTO(report);
    }

    @Override
    public ReportDTO updateReport(String reportId, ReportDTO reportDTO) {
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ResourceNotFoundException("Report not found with id " + reportId));
        report.updateFromDTO(reportDTO);
        report = reportRepository.save(report);
        return new ReportDTO(report);
    }

    @Override
    public ReportDTO assignReportToIssue(String reportId, String issueId) {
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ResourceNotFoundException("Report not found with id " + reportId));
        report.setIssue(issueRepository.findById(issueId)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id " + issueId)));
        report = reportRepository.save(report);
        return new ReportDTO(report);
    }

    @Override
    public List<ReportDTO> getReportsByIssue(String issueId) {
        return reportRepository.findByIssueId(issueId).stream()
            .map(ReportDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public CommentDTO addCommentToReport(String reportId, CommentDTO commentDTO) {
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ResourceNotFoundException("Report not found with id " + reportId));
        Comment comment = new Comment(commentDTO);
        comment.setReport(report);
        comment = commentRepository.save(comment);
        return new CommentDTO(comment);
    }

    @Override
    public ReportDTO changeStatusToReview(String reportId) {
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ResourceNotFoundException("Report not found with id " + reportId));
        report.setStatus(Status.EN_REVISION);
        report = reportRepository.save(report);
        return new ReportDTO(report);
    }

    @Override
    public ReportDTO assignCategoryToReport(String reportId, String categoryIssue) {
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ResourceNotFoundException("Report not found with id " + reportId));
        report.setCategoryIssue(CategoryIssue.valueOf(categoryIssue.toUpperCase()));
        report = reportRepository.save(report);
        return new ReportDTO(report);
    }
}
