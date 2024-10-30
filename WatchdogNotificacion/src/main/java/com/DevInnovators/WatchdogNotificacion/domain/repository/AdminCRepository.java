package com.DevInnovators.WatchdogNotificacion.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DevInnovators.WatchdogNotificacion.domain.model.AdminC;

@Repository
public interface AdminCRepository extends JpaRepository<AdminC, String> {
}