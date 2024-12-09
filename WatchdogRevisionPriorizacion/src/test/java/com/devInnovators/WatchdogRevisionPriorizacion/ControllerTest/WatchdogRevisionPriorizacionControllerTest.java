package com.devInnovators.WatchdogRevisionPriorizacion.ControllerTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devInnovators.WatchdogRevisionPriorizacion.application.DTO.IssueDTO;
import com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO.UpdateReportEvent;
import com.devInnovators.WatchdogRevisionPriorizacion.application.interfaces.RevisionPriorizacionServiceInterface;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.CategoryIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Priority;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.StatusIssue;
import com.devInnovators.WatchdogRevisionPriorizacion.infra.RevisionPriorizacionController;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RevisionPriorizacionController.class)
public class WatchdogRevisionPriorizacionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RevisionPriorizacionServiceInterface revisionPriorizacionService;

    // Test para crear un nuevo Issue
    @Test
    void testCreateIssue() throws Exception {
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId("1");
        issueDTO.setCategory(CategoryIssue.AGUA_Y_DRENAJE);
        issueDTO.setPriority(Priority.ALTA);

        when(revisionPriorizacionService.createIssue(any(IssueDTO.class))).thenReturn(issueDTO);

        mockMvc.perform(post("/api/v1/issues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(issueDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.category").value("AGUA_Y_DRENAJE"))
                .andExpect(jsonPath("$.priority").value("ALTA"));

        verify(revisionPriorizacionService, times(1)).createIssue(any(IssueDTO.class));
    }

    // Test para actualizar el estado de un Issue
    @Test
    void testUpdateIssueStatus() throws Exception {
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId("1");
        issueDTO.setStatusIssue(StatusIssue.ASIGNADO);

        when(revisionPriorizacionService.updateIssueStatus(eq("1"), eq(StatusIssue.ASIGNADO))).thenReturn(issueDTO);

        mockMvc.perform(put("/api/v1/issues/1/status")
                .param("status", "ASIGNADO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.statusIssue").value("ASIGNADO"));

        verify(revisionPriorizacionService, times(1)).updateIssueStatus(eq("1"), eq(StatusIssue.ASIGNADO));
    }

    // Test para obtener todos los Issues
    @Test
    void testGetAllIssues() throws Exception {
        IssueDTO issue1 = new IssueDTO();
        issue1.setId("1");
        issue1.setCategory(CategoryIssue.AGUA_Y_DRENAJE);
        issue1.setPriority(Priority.ALTA);

        IssueDTO issue2 = new IssueDTO();
        issue2.setId("2");
        issue2.setCategory(CategoryIssue.AGUA_Y_DRENAJE);
        issue2.setPriority(Priority.MEDIA);

        when(revisionPriorizacionService.getAllIssues()).thenReturn(List.of(issue1, issue2));

        mockMvc.perform(get("/api/v1/issues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));

        verify(revisionPriorizacionService, times(1)).getAllIssues();
    }

    // Test para actualizar un reporte y disparar el evento
    @Test
    void testUpdateReport() throws Exception {
        UpdateReportEvent event = new UpdateReportEvent();
        event.setDescription("Updated description");
        event.setCategoryIssue(CategoryIssue.AGUA_Y_DRENAJE);

        doNothing().when(revisionPriorizacionService).processUpdateReport(any(UpdateReportEvent.class));

        mockMvc.perform(put("/api/v1/issues/reports/{reportId}", "report1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk());

        verify(revisionPriorizacionService, times(1)).processUpdateReport(any(UpdateReportEvent.class));
    }
}
