package com.spring.securityapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDTO {
    private Long id;
    private String accessToken;
    private String refreshToken;
}
