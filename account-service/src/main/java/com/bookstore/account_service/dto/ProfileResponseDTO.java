package com.bookstore.account_service.dto;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponseDTO {
    private Long id;
    private String email;
    private String fullName;
    private boolean isAdmin;
    private boolean enabled;
}

