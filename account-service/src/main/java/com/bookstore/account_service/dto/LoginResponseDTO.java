package com.bookstore.account_service.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
	
    private String accessToken;
    private String tokenType = "Bearer";
}

