package ServiceTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.service.CommandReportServiceImpl;
import com.devInnovators.Whatchdog.Command.domain.model.Citizen;
import com.devInnovators.Whatchdog.Command.domain.model.Report;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandCitizenRepository;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandReportRepository;
import com.devInnovators.Whatchdog.Command.exception.ResourceNotFoundException;
import com.devInnovators.Whatchdog.Sync.serviceSync.SyncService;

@ExtendWith(MockitoExtension.class)
public class CommandReportServiceImplTest {

    @Mock
    private CommandReportRepository reportRepository;

    @Mock
    private CommandCitizenRepository citizenRepository;

    @Mock
    private SyncService syncService;


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
    public void testUpdateReport_success() {
        // Configurar datos de prueba
        String reportId = "123";
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setDescription("New description");

        Report existingReport = new Report();
        existingReport.set_id(reportId);
        existingReport.setDescription("Old description");

        // Configurar comportamiento de los mocks
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(existingReport));
        when(reportRepository.save(any(Report.class))).thenReturn(existingReport);

        // Aquí no importa el comportamiento real del método en el mock de syncService
        doNothing().when(syncService).syncReportById(reportId);

        // Ejecutar el método
        ReportDTO updatedReport = commandReportService.updateReport(reportId, reportDTO);

        // Verificar resultados
        assertNotNull(updatedReport);
        assertEquals("New description", updatedReport.getDescription());
        verify(syncService, times(1)).syncReportById(reportId);
    }



    @Test
    void testUpdateReport_notFound() {
        // Arrange
        String reportId = "nonExistentReportId";
        ReportDTO reportDTO = new ReportDTO();

        // Mockear que no se encuentra el reporte
        Mockito.lenient().when(reportRepository.findById(reportId))
        .thenReturn(Optional.empty());  // Devuelve un Optional vacío para simular que no se encuentra el reporte

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> commandReportService.updateReport(reportId, reportDTO)
        );

        // Verificar el mensaje de la excepción
        assertEquals("Reporte no encontrado con id: " + reportId, exception.getMessage());

        // Verificar que no se haya llamado save al no encontrar el reporte
        Mockito.verify(reportRepository, Mockito.never()).save(Mockito.any(Report.class));
        }
}
