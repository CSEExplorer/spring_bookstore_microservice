package com.bookstore.account_service.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.account_service.dto.LoginRequestDTO;
import com.bookstore.account_service.dto.LoginResponseDTO;
import com.bookstore.account_service.dto.RegisterRequestDTO;
import com.bookstore.account_service.dto.RegisterResponseDTO;
import com.bookstore.account_service.service.UserService;

@RestController
@RequestMapping("/account")

@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // 🟢 Register User
    @PostMapping("/register/user")
    public ResponseEntity<RegisterResponseDTO> registerUser(@RequestBody RegisterRequestDTO dto) {
    	dto.setAdmin(false);
        RegisterResponseDTO response = userService.registerUser(dto);
        return ResponseEntity.ok(response);
    }

    // 🟡 Register Admin
    @PostMapping("/register/admin")
    public ResponseEntity<RegisterResponseDTO> registerAdmin(@RequestBody RegisterRequestDTO dto) {
        dto.setAdmin(true);
        RegisterResponseDTO response = userService.registerUser(dto);
        return ResponseEntity.ok(response);
    }

    // 🔐 Login
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

    
}
