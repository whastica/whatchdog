package com.devInnovators.Whatchdog.Command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devInnovators.Whatchdog.Command.domain.model.AdminC;

public interface CommandAdminCRepository  extends JpaRepository<AdminC, String> {

    
} 