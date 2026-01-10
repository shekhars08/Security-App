package com.spring.securityapp.service;

import com.spring.securityapp.dto.SignInDTO;
import com.spring.securityapp.dto.SignInResponseDTO;
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
    private final UserService userService;
    private final SessionService sessionService;

    public SignInResponseDTO signIn(SignInDTO signInDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDTO.getEmail(),
                        signInDTO.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sessionService.generateNewSession(user, refreshToken);


        return new SignInResponseDTO(
                user.getId(),
                accessToken,
                refreshToken);
    }

    public SignInResponseDTO refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        UserEntity user = userService.getUserById(userId);


        String accessToken = jwtService.generateAccessToken(user);
        return new SignInResponseDTO(
                user.getId(),
                accessToken,
                refreshToken);

    }
}
