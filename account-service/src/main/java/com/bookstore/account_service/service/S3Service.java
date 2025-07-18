package com.bookstore.account_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadFile(MultipartFile file, String folderName);
    void deleteFile(String key);
    String generatePresignedUrl(String key);
}
