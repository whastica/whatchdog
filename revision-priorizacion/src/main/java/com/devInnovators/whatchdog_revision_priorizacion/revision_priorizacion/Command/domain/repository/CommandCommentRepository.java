package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.repository;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Command.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandCommentRepository extends JpaRepository<Comment, String> {
    // Métodos adicionales de consulta pueden añadirse aquí si son necesarios.
}
