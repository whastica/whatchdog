package com.DevInnovators.WatchdogNotificacion.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.DevInnovators.WatchdogNotificacion.domain.model.Notification;

public interface  NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByIdCitizen(String idCitizen);  // Para obtener notificaciones de un ciudadano
    List<Notification> findByIdAdmin(String idAdmin);      // Para obtener notificaciones de un administrador
}
