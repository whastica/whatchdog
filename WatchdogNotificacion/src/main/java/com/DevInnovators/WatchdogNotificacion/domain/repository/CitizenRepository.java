package com.DevInnovators.WatchdogNotificacion.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DevInnovators.WatchdogNotificacion.domain.model.Citizen;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, String> {
}
