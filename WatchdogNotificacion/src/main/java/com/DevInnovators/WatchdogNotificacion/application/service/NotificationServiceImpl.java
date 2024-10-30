package com.DevInnovators.WatchdogNotificacion.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DevInnovators.WatchdogNotificacion.domain.model.Notification;
import com.DevInnovators.WatchdogNotificacion.domain.repository.NotificationRepository;

@Service
public class NotificationServiceImpl {

    @Autowired
    private NotificationRepository notificacionRepository;

    // Crear una notificación
    public Notification createNotificacion(String description, String idAdmin, String idIssue, String idReport, String idCitizen) {
        Notification notification = new Notification();
        notification.setDescription(description);
        notification.setCreateDate(LocalDateTime.now());
        notification.setIdAdmin(idAdmin);
        notification.setIdIssue(idIssue);
        notification.setIdReport(idReport);
        notification.setIdCitizen(idCitizen);
        return notificacionRepository.save(notification);
    }

    // Obtener notificaciones de un ciudadano específico
    public List<Notification> getNotificacionesByCitizen(String idCitizen) {
        return notificacionRepository.findByIdCitizen(idCitizen);
    }

    // Obtener notificaciones de un administrador específico
    public List<Notification> getNotificacionesByAdmin(String idAdmin) {
        return notificacionRepository.findByIdAdmin(idAdmin);
    }

    // Obtener una notificación específica por ID
    public Optional<Notification> getNotificacionById(String id) {
        return notificacionRepository.findById(id);
    }

    // Eliminar una notificación específica
    public void deleteNotificacion(String id) {
        notificacionRepository.deleteById(id);
    }
}
