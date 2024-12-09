package ControllerTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.CommentDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.interfaces.CommandReportServiceInterface;
import com.devInnovators.Whatchdog.Command.infra.CommandReportController;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CommandReportController.class)
public class CommandReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommandReportServiceInterface reportService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateReport_success() throws Exception {
        // Arrange
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.set_id("report1");
        reportDTO.setDescription("New Report");
        reportDTO.setIdCitizen("citizen123");

        when(reportService.createReport(any(ReportDTO.class))).thenReturn(reportDTO);

        // Act & Assert
        mockMvc.perform(post("/api/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reportDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("report1"))
                .andExpect(jsonPath("$.description").value("New Report"))
                .andExpect(jsonPath("$.idCitizen").value("citizen123"));

        verify(reportService, times(1)).createReport(any(ReportDTO.class));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when creating a report without ID")
    void testCreateReport_missingId() throws Exception {
        // Arrange
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setDescription("New Report");

        // Act & Assert
        mockMvc.perform(post("/api/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reportDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("idReport is required"));
        
        verify(reportService, never()).createReport(any(ReportDTO.class));
    }

    @Test
    @DisplayName("Should update a report successfully")
    void testUpdateReport_success() throws Exception {
        // Arrange
        String reportId = "report1";
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.set_id(reportId);
        reportDTO.setDescription("Updated Report");

        when(reportService.updateReport(eq(reportId), any(ReportDTO.class))).thenReturn(reportDTO);

        // Act & Assert
        mockMvc.perform(put("/api/reports/{_id}", reportId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reportDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reportId))
                .andExpect(jsonPath("$.description").value("Updated Report"));

        verify(reportService, times(1)).updateReport(eq(reportId), any(ReportDTO.class));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when updating a report without ID")
    void testUpdateReport_missingId() throws Exception {
        // Arrange
        String reportId = "report1";
        ReportDTO reportDTO = new ReportDTO();

        // Act & Assert
        mockMvc.perform(put("/api/reports/{_id}", reportId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reportDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("_id is required"));

        verify(reportService, never()).updateReport(anyString(), any(ReportDTO.class));
    }

    @Test
    @DisplayName("Should delete a report successfully")
    void testDeleteReport_success() throws Exception {
        // Arrange
        String reportId = "report1";
        doNothing().when(reportService).deleteReport(reportId);

        // Act & Assert
        mockMvc.perform(delete("/api/reports/{idReport}", reportId))
                .andExpect(status().isNoContent());

        verify(reportService, times(1)).deleteReport(reportId);
    }

    @Test
    @DisplayName("Should create a comment successfully")
    void testCreateCommentToReport_success() throws Exception {
        // Arrange
        String reportId = "report1";
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId("comment1");
        commentDTO.setDescription("This is a comment");

        when(reportService.createCommentToReport(any(CommentDTO.class), eq(reportId))).thenReturn(commentDTO);

        // Act & Assert
        mockMvc.perform(post("/api/reports/{idReport}/comments", reportId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("comment1"))
                .andExpect(jsonPath("$.content").value("This is a comment"));

        verify(reportService, times(1)).createCommentToReport(any(CommentDTO.class), eq(reportId));
    }

    @Test
    @DisplayName("Should add a comment to a report successfully")
    void testAddCommentToReport_success() throws Exception {
        // Arrange
        String reportId = "report1";
        String commentId = "comment1";

        doNothing().when(reportService).addCommentToReport(reportId, commentId);

        // Act & Assert
        mockMvc.perform(put("/api/reports/{idReport}/comments/{idComment}", reportId, commentId))
                .andExpect(status().isOk());

        verify(reportService, times(1)).addCommentToReport(reportId, commentId);
    }
}
