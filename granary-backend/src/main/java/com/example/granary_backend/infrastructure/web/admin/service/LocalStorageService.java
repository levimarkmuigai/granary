package com.example.granary_backend.infrastructure.web.admin.service;

import com.example.granary_backend.application.service.FileStorageService;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

// NOTE: We don't use @Service here; it's managed by the StorageConfiguration
public class LocalStorageService implements FileStorageService {

    private final Path fileStorageLocation;
    private final String baseUrl;

    public LocalStorageService(String storageLocation, String baseUrl) {
        this.fileStorageLocation = Paths.get(storageLocation).toAbsolutePath().normalize();
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        
        // Use UUID to prevent name collisions
        String fileName = UUID.randomUUID().toString() + extension;
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        
        Files.copy(file.getInputStream(), targetLocation);
        
        // Return the full accessible URL
        return this.baseUrl + "images/" + fileName;
    }

    @Override
    public boolean deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) return false;
        
        // 1. Extract the file name from the URL
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        
        try {
            return Files.deleteIfExists(targetLocation);
        } catch (IOException e) {
            System.err.println("Failed to delete local file: " + fileName + ". Reason: " + e.getMessage());
            return false;
        }
    }
}
