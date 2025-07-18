package com.bookstore.account_service.service;



import org.springframework.web.multipart.MultipartFile;

import com.bookstore.account_service.dto.UserProfileDTO;

public interface UserProfileService {
    void createOrUpdateProfile(String email, UserProfileDTO dto);
    UserProfileDTO getProfile(String email);
    void deleteProfile(String email);
    String uploadProfileImage(String email, MultipartFile file);
    String getProfileImageUrl(String email);
    void deleteProfileImage(String email);
}
