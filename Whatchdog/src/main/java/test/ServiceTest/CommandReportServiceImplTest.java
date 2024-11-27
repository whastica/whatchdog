import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.service.CommandReportServiceImpl;
import com.devInnovators.Whatchdog.Command.domain.model.Citizen;
import com.devInnovators.Whatchdog.Command.domain.model.Report;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandCitizenRepository;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandReportRepository;
import com.devInnovators.Whatchdog.Command.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CommandReportServiceImplTest {

    @Mock
    private CommandReportRepository reportRepository;

    @Mock
    private CommandCitizenRepository citizenRepository;

    /*@Mock
    private CommandIssueRepository issueRepository;


    @Mock
    private CommandCommentRepository commentRepository;

    @Mock
    private SyncService syncService;*/

    @InjectMocks
    private CommandReportServiceImpl commandReportService;

    @Test
    void testCreateReport_success() {
        // Arrange
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.set_id("report123");
        reportDTO.setDescription("Report Description");
        reportDTO.setIdCitizen("citizen123");

        Citizen mockCitizen = new Citizen();
        mockCitizen.setId("citizen123");

        Report mockReport = new Report();
        mockReport.set_id("report123");
        mockReport.setDescription("Report Description");
        mockReport.setCitizen(mockCitizen);

        Mockito.when(citizenRepository.findById("citizen123"))
                .thenReturn(Optional.of(mockCitizen));
        Mockito.when(reportRepository.save(Mockito.any(Report.class)))
                .thenReturn(mockReport);

        // Act
        ReportDTO createdReport = commandReportService.createReport(reportDTO);

        // Assert
        assertNotNull(createdReport);
        assertEquals("report123", createdReport.get_id());
        assertEquals("Report Description", createdReport.getDescription());
        Mockito.verify(reportRepository, Mockito.times(1)).save(Mockito.any(Report.class));
    }

    @Test
    void testCreateReport_citizenNotFound() {
        // Arrange
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.set_id("report123");
        reportDTO.setDescription("Report Description");
        reportDTO.setIdCitizen("invalidCitizenId");

        Mockito.when(citizenRepository.findById("invalidCitizenId"))
                .thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> commandReportService.createReport(reportDTO)
        );

        assertEquals("Ciudadano no encontrado con id: invalidCitizenId", exception.getMessage());
        Mockito.verify(reportRepository, Mockito.never()).save(Mockito.any(Report.class));
    }

    @Test
    void testUpdateReport_success() {
        // Arrange
        String reportId = "report123";
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setDescription("Updated Description");

        Report mockExistingReport = new Report();
        mockExistingReport.set_id(reportId);
        mockExistingReport.setDescription("Old Description");

        Mockito.when(reportRepository.findById(reportId))
                .thenReturn(Optional.of(mockExistingReport));
        Mockito.when(reportRepository.save(Mockito.any(Report.class)))
                .thenReturn(mockExistingReport);

        // Act
        ReportDTO updatedReport = commandReportService.updateReport(reportId, reportDTO);

        // Assert
        assertNotNull(updatedReport);
        assertEquals("Updated Description", updatedReport.getDescription());
        Mockito.verify(reportRepository, Mockito.times(1)).save(Mockito.any(Report.class));
    }

    @Test
    void testUpdateReport_notFound() {
        // Arrange
        String reportId = "nonExistentReportId";
        ReportDTO reportDTO = new ReportDTO();

        Mockito.when(reportRepository.findById(reportId))
                .thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> commandReportService.updateReport(reportId, reportDTO)
        );

        assertEquals("Reporte no encontrado con id: nonExistentReportId", exception.getMessage());
        Mockito.verify(reportRepository, Mockito.never()).save(Mockito.any(Report.class));
    }
}
