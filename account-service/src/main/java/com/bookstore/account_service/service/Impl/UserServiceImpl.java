package com.bookstore.account_service.service.Impl;


import lombok.RequiredArgsConstructor;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.account_service.dto.LoginRequestDTO;
import com.bookstore.account_service.dto.LoginResponseDTO;
import com.bookstore.account_service.dto.RegisterRequestDTO;
import com.bookstore.account_service.dto.RegisterResponseDTO;
import com.bookstore.account_service.entity.User;
import com.bookstore.account_service.entity.UserProfile;
import com.bookstore.account_service.repository.UserRepository;
import com.bookstore.account_service.service.UserService;
import com.bookstore.account_service.util.JwtService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public RegisterResponseDTO registerUser(RegisterRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
               
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .isAdmin(false)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        UserProfile profile = UserProfile.builder()
                 // Or leave null
        		
                .user(user)
                .build();

        // Step 3: Attach profile to user (to enable cascading)
        user.setProfile(profile);

        userRepository.save(user);
        
        return RegisterResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
               .isAdmin(user.isAdmin())
                .enabled(user.isEnabled())
                .build();
    }

    @Override
    public RegisterResponseDTO registerAdmin(RegisterRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
               
                .isAdmin(true)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        
        UserProfile profile = UserProfile.builder()
                // Or leave null
               .user(user)
               .build();

       // Step 3: Attach profile to user (to enable cascading)
       user.setProfile(profile);

        return RegisterResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                
                .isAdmin(user.isAdmin())
                .enabled(user.isEnabled())
                .build();
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail(), user.isAdmin());

        return new LoginResponseDTO(token,"Login Sucessfully");
    }

   

	
}

