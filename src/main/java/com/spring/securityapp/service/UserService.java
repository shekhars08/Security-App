package com.spring.securityapp.service;

import com.spring.securityapp.dto.SignInDTO;
import com.spring.securityapp.dto.SignUpDTO;
import com.spring.securityapp.dto.UserDTO;
import com.spring.securityapp.entities.UserEntity;
import com.spring.securityapp.exceptions.ResourceNotFoundException;
import com.spring.securityapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + username + " not found"));
    }

    public UserDTO signup(SignUpDTO signUpDTO) {
        Optional<UserEntity> user = userRepository.findByEmail(signUpDTO.getEmail());
        if (user.isPresent()) {
            throw new BadCredentialsException("User with email already exists "+ signUpDTO.getEmail());
        }

        UserEntity createNewUser = modelMapper.map(signUpDTO, UserEntity.class);
        createNewUser.setPassword(passwordEncoder.encode(createNewUser.getPassword()));
        UserEntity savedUser = userRepository.save(createNewUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }
}
