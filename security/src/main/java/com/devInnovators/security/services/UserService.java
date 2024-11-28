package com.devInnovators.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    
    UserDetailsService userDetailsService();
}
