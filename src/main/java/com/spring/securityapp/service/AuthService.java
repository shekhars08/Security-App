package com.spring.securityapp.service;

import com.spring.securityapp.dto.SignInDTO;
import com.spring.securityapp.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String signIn(SignInDTO signInDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDTO.getEmail(),
                        signInDTO.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();
        return jwtService.generateToken(user);
    }
}
