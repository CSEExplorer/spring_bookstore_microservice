package com.bookstore.account_service.service;



import com.bookstore.account_service.dto.LoginRequestDTO;
import com.bookstore.account_service.dto.LoginResponseDTO;
import com.bookstore.account_service.dto.RegisterRequestDTO;
import com.bookstore.account_service.dto.RegisterResponseDTO;


public interface UserService {
    RegisterResponseDTO registerUser(RegisterRequestDTO dto);
    RegisterResponseDTO registerAdmin(RegisterRequestDTO dto);
    LoginResponseDTO login(LoginRequestDTO dto);
    
}
