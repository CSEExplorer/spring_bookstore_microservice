package com.bookstore.account_service.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponseDTO {
    private Long id;
    private String email;
    private String fullName;
    private boolean isAdmin;
    private boolean enabled;
    private UserProfileDTO profile;
}
