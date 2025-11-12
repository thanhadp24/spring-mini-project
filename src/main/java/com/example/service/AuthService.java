package com.example.service;

import com.example.dto.auth.AuthRequest;
import com.example.dto.auth.AuthResponse;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthResponse login(AuthRequest authReq){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));
        UserDetails userDetails = customUserDetailService.loadUserByUsername(authReq.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);

    }

    @Transactional
    public String register(AuthRequest authReq){
        if(userRepository.findByUsername(authReq.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        authReq.setPassword(passwordEncoder.encode(authReq.getPassword()));
        User user = new User();
        user.setUsername(authReq.getUsername());
        user.setPassword(authReq.getPassword());
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(Collections.singletonList(role));
        userRepository.save(user);
        return "User registered with username: " + authReq.getUsername();
    }
}
