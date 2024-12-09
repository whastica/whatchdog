package com.devInnovators.WatchdogRevisionPriorizacion.ServiceTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.devInnovators.WatchdogRevisionPriorizacion.application.DTO.IssueDTO;
import com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO.UpdateReportEvent;
import com.devInnovators.WatchdogRevisionPriorizacion.application.service.RevisionPriorizacionServiceImpl;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.CategoryIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Issue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Priority;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Report;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.StatusIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.repository.IssueRepository;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.repository.ReportRepository;
import com.devInnovators.WatchdogRevisionPriorizacion.infra.publisher.EventPublisher;

class RevisionPriorizacionServiceImplTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private RevisionPriorizacionServiceImpl revisionPriorizacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateIssue() {
        // Arrange
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId("1");
        issueDTO.setCategory(CategoryIssue.AGUA_Y_DRENAJE);
        issueDTO.setPriority(Priority.ALTA);

        Issue issue = new Issue();
        issue.setId("1");
        issue.setCategoryIssue(CategoryIssue.AGUA_Y_DRENAJE);
        issue.setPriority(Priority.ALTA);
        issue.setStatusIssue(StatusIssue.ASIGNADO);

        when(issueRepository.save(any(Issue.class))).thenReturn(issue);

        // Act
        IssueDTO result = revisionPriorizacionService.createIssue(issueDTO);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals(CategoryIssue.AGUA_Y_DRENAJE, result.getCategory());
        assertEquals(Priority.ALTA, result.getPriority());
        assertEquals(StatusIssue.ASIGNADO, result.getStatusIssue());

        verify(issueRepository, times(1)).save(any(Issue.class));
    }

    @Test
    void testUpdateIssueStatus() {
        // Arrange
        String issueId = "1";
        StatusIssue newStatus = StatusIssue.ASIGNADO;

        Issue issue = new Issue();
        issue.setId(issueId);
        issue.setStatusIssue(StatusIssue.ASIGNADO);

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));
        when(issueRepository.save(any(Issue.class))).thenReturn(issue);

        // Act
        IssueDTO result = revisionPriorizacionService.updateIssueStatus(issueId, newStatus);

        // Assert
        assertNotNull(result);
        assertEquals(issueId, result.getId());
        assertEquals(newStatus, result.getStatusIssue());

        verify(issueRepository, times(1)).findById(issueId);
        verify(issueRepository, times(1)).save(any(Issue.class));
    }

    @Test
    void testGetAllIssues() {
        // Arrange
        Issue issue1 = new Issue();
        issue1.setId("1");
        issue1.setCategoryIssue(CategoryIssue.AGUA_Y_DRENAJE);
        issue1.setPriority(Priority.ALTA);
        issue1.setStatusIssue(StatusIssue.ASIGNADO);

        Issue issue2 = new Issue();
        issue2.setId("2");
        issue2.setCategoryIssue(CategoryIssue.AGUA_Y_DRENAJE);
        issue2.setPriority(Priority.MEDIA);
        issue2.setStatusIssue(StatusIssue.EN_PROCESO);

        when(issueRepository.findAll()).thenReturn(List.of(issue1, issue2));

        // Act
        List<IssueDTO> result = revisionPriorizacionService.getAllIssues();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals(CategoryIssue.AGUA_Y_DRENAJE, result.get(0).getCategory());
        assertEquals(Priority.ALTA, result.get(0).getPriority());
        assertEquals(StatusIssue.ASIGNADO, result.get(0).getStatusIssue());

        verify(issueRepository, times(1)).findAll();
    }

    @Test
    void testProcessUpdateReport() {
        // Arrange
        UpdateReportEvent event = new UpdateReportEvent();
        event.setId("report1");
        event.setDescription("Updated description");
        event.setCategoryIssue(CategoryIssue.AGUA_Y_DRENAJE);

        Report report = new Report();
        report.set_id("report1");

        // Configurar los mocks
        when(reportRepository.findById("report1")).thenReturn(Optional.of(report));
        when(reportRepository.save(any(Report.class))).thenReturn(report);

        // Act
        assertDoesNotThrow(() -> revisionPriorizacionService.processUpdateReport(event));

        // Assert
        verify(reportRepository, times(1)).findById("report1");
        verify(reportRepository, times(1)).save(any(Report.class));
        verify(eventPublisher, times(1)).publishUpdateReportEvent(event); // Verifica que se llamó al publisher
    }
}
