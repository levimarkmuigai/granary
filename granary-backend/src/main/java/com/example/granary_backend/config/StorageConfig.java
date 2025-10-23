package com.example.granary_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.granary_backend.application.service.FileStorageService;
import com.example.granary_backend.infrastructure.web.admin.service.LocalStorageService;
import com.example.granary_backend.infrastructure.web.admin.service.S3FileStorageService;

@Configuration
public class StorageConfig {

    @Bean
    @ConditionalOnProperty(name = "storage.service", havingValue = "local", matchIfMissing = true)
    @Profile({"dev", "test"})
    public FileStorageService localStorageService(
            @Value("${storage.location}") String storageLocation,
            @Value("${app.base-url:#{null}}") String baseUrl) {

        System.out.println("--- ACTIVATING LOCAL FILE STORAGE SERVICE ---");
        return new LocalStorageService(storageLocation, baseUrl);
    }

    @Bean
    @ConditionalOnProperty(name = "storage.service", havingValue = "s3")
    @Profile("prod")
    public FileStorageService s3FileStorageService() {
        System.out.println("--- ACTIVATING S3 CLOUD STORAGE SERVICE (PROD) ---");
        return new S3FileStorageService();
    }
}
