package ControllerTest;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Query.application.interfaces.QueryReportServiceInterface;
import com.devInnovators.Whatchdog.Query.domain.model.QueryStatus;
import com.devInnovators.Whatchdog.Query.infra.QueryReportController;
import com.devInnovators.Whatchdog.WhatchdogApplication;


@WebMvcTest(QueryReportController.class)
@ContextConfiguration(classes = WhatchdogApplication.class)
public class QueryReportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QueryReportServiceInterface reportService;

    @Test
    void testGetReporteById_success() throws Exception {
        final String ID_REPORT = "report1";
        final String DESCRIPTION = "Report Description";
        final String ID_CITIZEN = "citizen123";

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setIdReport(ID_REPORT);
        reportDTO.setDescription(DESCRIPTION);
        reportDTO.setIdcitizen(ID_CITIZEN);

        when(reportService.findByIdReport(ID_REPORT)).thenReturn(reportDTO);

        mockMvc.perform(get("/api/reports/{idReport}", ID_REPORT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReport").value(ID_REPORT))
                .andExpect(jsonPath("$.description").value(DESCRIPTION))
                .andExpect(jsonPath("$.idcitizen").value(ID_CITIZEN));
    }

    @Test
    void testGetReportes_success() throws Exception {
        // Arrange
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setIdReport("report1");

        List<ReportDTO> reports = Collections.singletonList(reportDTO);

        when(reportService.findAllReports()).thenReturn(reports);

        // Act & Assert
        mockMvc.perform(get("/api/reports")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idReport").value("report1"));
    }

    @Test
    void testGetReportesByStatus_success() throws Exception {
        // Arrange
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setIdReport("report2");
        reportDTO.setStatus(QueryStatus.EN_REVISION);  // Asigna el valor de 'status'

        List<ReportDTO> reports = Collections.singletonList(reportDTO);

        when(reportService.findReportsByStatus(QueryStatus.EN_REVISION)).thenReturn(reports);

        // Act & Assert
        mockMvc.perform(get("/api/reports/status/{status}", "EN_REVISION")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idReport").value("report2"))
                .andExpect(jsonPath("$[0].status").value("EN_REVISION"));
    }

    @Test
    void testGetReportesByCategoryIssue_success() throws Exception {
        // Arrange
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setIdReport("report3");
        reportDTO.setCategoryIssue("Road Damage");

        List<ReportDTO> reports = Collections.singletonList(reportDTO);

        when(reportService.getReportsByCategoryIssue("Road Damage")).thenReturn(reports);

        // Act & Assert
        mockMvc.perform(get("/api/reports/category/{categoryIssue}", "Road Damage")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idReport").value("report3"))
                .andExpect(jsonPath("$[0].categoryIssue").value("Road Damage"));
    }

    @Test
    void testGetReportesByAdminId_success() throws Exception {
        // Arrange
        String idAdminC = "admin1";
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setIdReport("report4");

        List<ReportDTO> reports = Collections.singletonList(reportDTO);

        when(reportService.getReportsByAdminId(idAdminC)).thenReturn(reports);

        // Act & Assert
        mockMvc.perform(get("/api/reports/admin/{idAdminC}", idAdminC)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idReport").value("report4"));
 // Esta línea debería pasar ahora
    }
}
