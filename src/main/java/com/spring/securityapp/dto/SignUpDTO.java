package com.spring.securityapp.dto;

import com.spring.securityapp.entities.enums.Permissions;
import com.spring.securityapp.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {

    private String name;
    private String email;
    private String password;
    private Set<Role> roles;
    private Set<Permissions> permissions;
}
