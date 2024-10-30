package com.DevInnovators.WatchdogNotificacion.application.interfaces;
import java.util.List;
import java.util.Optional;

import com.DevInnovators.WatchdogNotificacion.domain.model.Notification;

public interface NotificationServiceInterface {

     // Crear una notificación
     Notification createNotificacion(String description, String idAdmin, String idIssue, String idReport, String idCitizen);

     // Obtener notificaciones de un ciudadano específico
     List<Notification> getNotificacionesByCitizen(String idCitizen);
 
     // Obtener notificaciones de un administrador específico
     List<Notification> getNotificacionesByAdmin(String idAdmin);
 
     // Obtener una notificación específica por ID
     Optional<Notification> getNotificacionById(String id);
 
     // Eliminar una notificación específica
     void deleteNotificacion(String id);

}
