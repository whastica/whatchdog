package com.devInnovators.security.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.devInnovators.security.repository.UserRepository;
import com.devInnovators.security.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
                
                @Override
                public UserDetails loadUserByUsername(String username) {
                    return userRepository.findByEmail(username)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                }
        };
    }
}
