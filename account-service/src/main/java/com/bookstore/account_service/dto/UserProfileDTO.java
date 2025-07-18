package com.bookstore.account_service.dto;


import lombok.Data;

@Data
public class UserProfileDTO {
    private String fullName;
    private String address;
    private String phone;
}
