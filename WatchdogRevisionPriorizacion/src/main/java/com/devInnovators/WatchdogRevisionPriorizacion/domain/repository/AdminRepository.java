package com.devInnovators.WatchdogRevisionPriorizacion.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.AdminC;

public interface AdminRepository extends JpaRepository<AdminC, String> {
}