package com.spring.securityapp.controller;

import com.spring.securityapp.dto.SignInDTO;
import com.spring.securityapp.dto.SignUpDTO;
import com.spring.securityapp.dto.UserDTO;
import com.spring.securityapp.service.AuthService;
import com.spring.securityapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){

        UserDTO userDTO = userService.signup(signUpDTO);
        return ResponseEntity.ok(userDTO);

    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody SignInDTO signInDTO){
        String token = authService.signIn(signInDTO);
        return ResponseEntity.ok(token);
    }
}
