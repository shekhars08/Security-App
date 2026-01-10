package com.spring.securityapp.controller;

import com.spring.securityapp.dto.SignInDTO;
import com.spring.securityapp.dto.SignInResponseDTO;
import com.spring.securityapp.dto.SignUpDTO;
import com.spring.securityapp.dto.UserDTO;
import com.spring.securityapp.service.AuthService;
import com.spring.securityapp.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {

        UserDTO userDTO = userService.signup(signUpDTO);
        return ResponseEntity.ok(userDTO);

    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDTO> signIn(
            @RequestBody SignInDTO signInDTO,
            HttpServletRequest request,
            HttpServletResponse response) {

        SignInResponseDTO  signInResponseDTO = authService.signIn(signInDTO);

        Cookie cookie = new Cookie("refreshToken", signInResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(signInResponseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<SignInResponseDTO> refreshToken(HttpServletRequest httpServletRequest){
        String refreshToken = Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token not found inside the cookie"));

        SignInResponseDTO signInResponseDTO = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(signInResponseDTO);
    }
}
