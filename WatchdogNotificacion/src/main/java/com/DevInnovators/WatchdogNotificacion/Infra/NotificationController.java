package com.DevInnovators.WatchdogNotificacion.Infra;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DevInnovators.WatchdogNotificacion.application.DTO.NotificationDTO;
import com.DevInnovators.WatchdogNotificacion.application.interfaces.NotificationServiceInterface;
import com.DevInnovators.WatchdogNotificacion.domain.model.Notification;


@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificationController {

    @Autowired
    private NotificationServiceInterface notificacionService;

    // Crear una notificación
    @PostMapping
    public Notification createNotificacion(@RequestBody NotificationDTO notificacionDto) {
        return notificacionService.createNotificacion(
                notificacionDto.getDescription(),
                notificacionDto.getIdAdmin(),
                notificacionDto.getIdIssue(),
                notificacionDto.getIdReport(),
                notificacionDto.getIdCitizen()
        );
    }

    // Obtener todas las notificaciones de un ciudadano específico
    @GetMapping("/ciudadano/{idCitizen}")
    public List<Notification> getNotificacionesByCitizen(@PathVariable String idCitizen) {
        return notificacionService.getNotificacionesByCitizen(idCitizen);
    }

    // Obtener todas las notificaciones de un administrador específico
    @GetMapping("/admin/{idAdmin}")
    public List<Notification> getNotificacionesByAdmin(@PathVariable String idAdmin) {
        return notificacionService.getNotificacionesByAdmin(idAdmin);
    }

    // Obtener una notificación específica por ID
    @GetMapping("/{id}")
    public Optional<Notification> getNotificacionById(@PathVariable String id) {
        return notificacionService.getNotificacionById(id);
    }

    // Eliminar una notificación específica
    @DeleteMapping("/{id}")
    public void deleteNotificacion(@PathVariable String id) {
        notificacionService.deleteNotificacion(id);
    }
}