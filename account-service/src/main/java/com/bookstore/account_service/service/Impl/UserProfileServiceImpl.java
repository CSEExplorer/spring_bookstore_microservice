package com.bookstore.account_service.service.Impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.account_service.dto.UserProfileDTO;
import com.bookstore.account_service.entity.User;
import com.bookstore.account_service.entity.UserProfile;
import com.bookstore.account_service.repository.UserProfileRepository;
import com.bookstore.account_service.repository.UserRepository;
import com.bookstore.account_service.service.S3Service;
import com.bookstore.account_service.service.UserProfileService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final S3Service s3Service;

    private static final String FOLDER = "profile-images";

    @Override
    public void createOrUpdateProfile(String email, UserProfileDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<UserProfile> existingProfileOpt = userProfileRepository.findByUser(user);

        if (existingProfileOpt.isPresent()) {
            UserProfile profile = existingProfileOpt.get();
            profile.setFullName(dto.getFullName());
            profile.setAddress(dto.getAddress());
            profile.setPhone(dto.getPhone());
            userProfileRepository.save(profile);
        } else {
            UserProfile profile = UserProfile.builder()
                    .user(user)
                    .fullName(dto.getFullName())
                    .address(dto.getAddress())
                    .phone(dto.getPhone())
                    .build();
            userProfileRepository.save(profile);
        }
    }

    @Override
    public UserProfileDTO getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        UserProfileDTO dto = new UserProfileDTO();
        dto.setFullName(profile.getFullName());
        dto.setAddress(profile.getAddress());
        dto.setPhone(profile.getPhone());
        return dto;
    }

    @Override
    public void deleteProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userProfileRepository.findByUser(user).ifPresent(profile -> {
            if (profile.getImageKey() != null) {
                s3Service.deleteFile(profile.getImageKey());
            }
            userProfileRepository.delete(profile);
        });

        userRepository.delete(user); // Optional: Also delete user account
    }

    @Override
    public String uploadProfileImage(String email, MultipartFile file) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // Delete previous image
        if (profile.getImageKey() != null) {
            s3Service.deleteFile(profile.getImageKey());
        }

        // Upload new image
        String key = s3Service.uploadFile(file, FOLDER);
        String url = s3Service.generatePresignedUrl(key);

        profile.setImageKey(key);
        profile.setImageUrl(url);
        userProfileRepository.save(profile);

        return url;
    }

    @Override
    public String getProfileImageUrl(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (profile.getImageKey() == null) {
            throw new RuntimeException("No image available");
        }

        return s3Service.generatePresignedUrl(profile.getImageKey());
    }

    @Override
    public void deleteProfileImage(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (profile.getImageKey() != null) {
            s3Service.deleteFile(profile.getImageKey());
            profile.setImageKey(null);
            profile.setImageUrl(null);
            userProfileRepository.save(profile);
        }
    }

	
}
