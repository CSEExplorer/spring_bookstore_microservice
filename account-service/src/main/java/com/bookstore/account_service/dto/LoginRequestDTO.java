package com.bookstore.account_service.dto;


import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
