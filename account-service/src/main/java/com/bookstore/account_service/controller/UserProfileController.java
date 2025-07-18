package com.bookstore.account_service.controller;

import com.bookstore.account_service.dto.UserProfileDTO;
import com.bookstore.account_service.service.UserProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    // 🟢 Create or Update Profile
    @PostMapping
    public ResponseEntity<String> createOrUpdateProfile(HttpServletRequest request,
                                                        @RequestBody UserProfileDTO dto) {
    	
        String email = request.getHeader("X-User-Email");
        userProfileService.createOrUpdateProfile(email, dto);
        return ResponseEntity.ok("Profile saved successfully");
    }

    // 🟣 Get Profile
    @GetMapping
    public ResponseEntity<UserProfileDTO> getProfile(HttpServletRequest request) {
        String email = request.getHeader("X-User-Email");
        UserProfileDTO dto = userProfileService.getProfile(email);
        return ResponseEntity.ok(dto);
    }

   
    @DeleteMapping
    public ResponseEntity<String> deleteProfile(HttpServletRequest request) {
        String email = request.getHeader("X-User-Email");
        userProfileService.deleteProfile(email);
        return ResponseEntity.ok("Profile and user deleted successfully");
    }

    // 🖼️ Upload Profile Image
    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(HttpServletRequest request,
                                              @RequestParam("file") MultipartFile file) {
        String email = request.getHeader("X-User-Email");
        String imageUrl = userProfileService.uploadProfileImage(email, file);
        return ResponseEntity.ok(imageUrl);
    }

    // 📷 Get Profile Image URL
    @GetMapping("/image")
    public ResponseEntity<String> getImage(HttpServletRequest request) {
        String email = request.getHeader("X-User-Email");
        String url = userProfileService.getProfileImageUrl(email);
        return ResponseEntity.ok(url);
    }

    // ❌ Delete Image
    @DeleteMapping("/image")
    public ResponseEntity<String> deleteImage(HttpServletRequest request) {
        String email = request.getHeader("X-User-Email");
        userProfileService.deleteProfileImage(email);
        return ResponseEntity.ok("Profile image deleted");
    }
}
