package com.devInnovators.security.repository;

import com.devInnovators.security.entities.Role;
import com.devInnovators.security.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByEmail(String email);

    User findByRole(Role email);
}
