package com.bookstore.account_service.dto;


import lombok.Data;

@Data
public class RegisterRequestDTO {
  
    private String email;
    private String password;
    private boolean isAdmin;
}
