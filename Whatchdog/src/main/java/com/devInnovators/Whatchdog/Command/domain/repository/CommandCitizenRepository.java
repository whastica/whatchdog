package com.devInnovators.Whatchdog.Command.domain.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devInnovators.Whatchdog.Command.domain.model.Citizen;

public interface CommandCitizenRepository extends JpaRepository<Citizen, String> {
    // Aquí puedes agregar métodos adicionales de consulta si es necesario
}
