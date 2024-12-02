package com.devInnovators.security.services;

import com.devInnovators.security.dto.JwtAuthenticationResponse;
import com.devInnovators.security.dto.RefreshTokenRequest;
import com.devInnovators.security.dto.SignInRequest;
import com.devInnovators.security.dto.SignUpRequest;
import com.devInnovators.security.entities.User;

public interface AuthenticationService {
    
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
