package ServiceTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Query.application.EventsDTO.RevisedReportEvent;
import com.devInnovators.Whatchdog.Query.application.service.QueryReportServiceImpl;
import com.devInnovators.Whatchdog.Query.domain.model.QueryCategoryIssue;
import com.devInnovators.Whatchdog.Query.domain.model.QueryReport;
import com.devInnovators.Whatchdog.Query.domain.model.QueryStatus;
import com.devInnovators.Whatchdog.Query.domain.repository.QueryReportRepository;

@ExtendWith(MockitoExtension.class)
public class QueryReportServiceImplTest {

    @Mock
    private QueryCategoryIssue categoryIssue;

    @Mock
    private QueryReportRepository reportRepository;

    @InjectMocks
    private QueryReportServiceImpl queryReportService;

    @Test
    void testFindByIdReport_success() {
        // Arrange
        String reportId = "report123";
        QueryReport mockReport = new QueryReport();
        mockReport.setIdReport(reportId);
        mockReport.setDescription("Test Description");

        when(reportRepository.findByIdReport(reportId)).thenReturn(mockReport);

        // Act
        ReportDTO result = queryReportService.findByIdReport(reportId);

        // Assert
        assertNotNull(result);
        assertEquals(reportId, result.getIdReport());
        assertEquals("Test Description", result.getDescription());
    }

    @Test
    void testFindByIdReport_notFound() {
        // Arrange
        String reportId = "nonExistentReportId";
        when(reportRepository.findByIdReport(reportId)).thenReturn(null);

        // Act & Assert
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> queryReportService.findByIdReport(reportId)
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Report not found with ID: " + reportId, exception.getReason());
    }

    @Test
    void testFindAllReports_success() {
        // Arrange
        QueryReport report1 = new QueryReport();
        report1.setIdReport("report1");
        report1.setDescription("Report 1 Description");

        QueryReport report2 = new QueryReport();
        report2.setIdReport("report2");
        report2.setDescription("Report 2 Description");

        List<QueryReport> mockReports = Arrays.asList(report1, report2);
        when(reportRepository.findAll()).thenReturn(mockReports);

        // Act
        List<ReportDTO> result = queryReportService.findAllReports();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("report1", result.get(0).getIdReport());
        assertEquals("Report 1 Description", result.get(0).getDescription());
    }

    @Test
    void testFindAllReports_noReports() {
        // Arrange
        when(reportRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> queryReportService.findAllReports()
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("No reports found", exception.getReason());
    }

    @Test
    void testFindReportsByStatus_success() {
        // Arrange
        QueryStatus status = QueryStatus.EN_REVISION; // Cambiar a QueryStatus en lugar de String
        QueryReport report = new QueryReport();
        report.setIdReport("report1");
        report.setStatus(status); // Asignar QueryStatus en lugar de String

        List<QueryReport> mockReports = Collections.singletonList(report);
        when(reportRepository.findByStatus(status)).thenReturn(mockReports);

        // Act
        List<ReportDTO> result = queryReportService.findReportsByStatus(status); // Pasar el QueryStatus

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(status, result.get(0).getStatus());
    }

    @Test
    void testFindReportsByStatus_noReports() {
        // Arrange
        QueryStatus status = QueryStatus.EN_REVISION; // Usa EN_REVISION en lugar de PENDING
        when(reportRepository.findByStatus(status)).thenReturn(Collections.emptyList());

        // Act & Assert
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> queryReportService.findReportsByStatus(status)
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("No reports found with status: " + status.name(), exception.getReason());
    }

    @Test
    void testGetReportsByCategoryIssue_success() {
        // Arrange
        categoryIssue = QueryCategoryIssue.INFRAESTRUCTURA_VIAL;
        
        // Crear un reporte con la categoría
        QueryReport report = new QueryReport();
        report.setIdReport("report1");
        report.setCategoryIssue(categoryIssue.name());  // Asignar como String el nombre del enum

        // Mockear la respuesta del repositorio
        List<QueryReport> mockReports = Collections.singletonList(report);
        when(reportRepository.findByCategoryIssue(categoryIssue.name())).thenReturn(mockReports);

        // Act
        List<ReportDTO> result = queryReportService.getReportsByCategoryIssue(categoryIssue.name());

        // Assert
        assertNotNull(result);  // Verificar que el resultado no sea nulo
        assertEquals(1, result.size());  // Verificar que solo haya un reporte
        // Verificar que la categoría del reporte se haya asignado correctamente al DTO
        assertEquals(categoryIssue.name(), result.get(0).getCategoryIssue());  
    }

    @Test
    void testGetReportsByCategoryIssue_noReports() {
        // Arrange
        categoryIssue = QueryCategoryIssue.INFRAESTRUCTURA_VIAL;
        when(reportRepository.findByCategoryIssue(categoryIssue.name())).thenReturn(Collections.emptyList());

        // Act & Assert
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> queryReportService.getReportsByCategoryIssue(categoryIssue.name())
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("No reports found with category issue: INFRAESTRUCTURA_VIAL", exception.getReason());
    }

    @Test
    void testUpdateReportStatus_success() {
        // Arrange
        RevisedReportEvent event = new RevisedReportEvent();
        event.setId("report123");  // Establecer el ID del reporte
        event.setStatus(QueryStatus.EN_PROCESO);  // Establecer el estado
        event.setCategoryIssue(QueryCategoryIssue.INFRAESTRUCTURA_VIAL);  // Establecer la categoría del problema

        // Crear el reporte mockeado que se devolverá cuando se busque por ID
        QueryReport mockReport = new QueryReport();
        mockReport.setIdReport("report123");
        mockReport.setStatus(QueryStatus.EN_REVISION);  // Estado inicial para comparar después

        // Mockear las respuestas del repositorio
        when(reportRepository.findByIdReport("report123")).thenReturn(mockReport);
        when(reportRepository.save(mockReport)).thenReturn(mockReport);  // El repositorio devuelve el reporte con la actualización

        // Act
        queryReportService.updateReportStatus(event);  // Llamar al método para actualizar el estado

        // Assert
        // Verificar que el estado se actualizó correctamente
        assertEquals(QueryStatus.EN_PROCESO, mockReport.getStatus());
        
        // Verificar que la categoría del problema también se actualizó correctamente (comparing as strings)
        assertEquals(QueryCategoryIssue.INFRAESTRUCTURA_VIAL.name(), mockReport.getCategoryIssue());  // Comparar como cadenas
        
        // Verificar que el repositorio haya guardado el reporte una vez
        verify(reportRepository, times(1)).save(mockReport);
    }
    @Test
    void testUpdateReportStatus_reportNotFound() {
        // Arrange
        RevisedReportEvent event = new RevisedReportEvent();
        event.setId("nonExistentReportId");
    
        // Usar enums en lugar de cadenas de texto
        event.setStatus(QueryStatus.EN_PROCESO); // Cambiado a enum QueryStatus
        event.setCategoryIssue(QueryCategoryIssue.INFRAESTRUCTURA_VIAL); // Cambiado a enum QueryCategoryIssue
    
        // Configurar el repositorio para devolver null, simulando que el reporte no existe
        when(reportRepository.findByIdReport("nonExistentReportId")).thenReturn(null);
    
        // Act & Assert
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> queryReportService.updateReportStatus(event)
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Report not found in Query database", exception.getReason());
    }
}