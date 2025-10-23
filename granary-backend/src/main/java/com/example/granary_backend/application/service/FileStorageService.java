package com.example.granary_backend.application.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileStorageService {

    /**
     * Uploads a file and returns the public URL/path.
     * 
     * @param file The file to upload.
     * @return The URL or path to the stored file.
     * @throws IOException if file operations fail.
     */
    String uploadFile(MultipartFile file) throws IOException;

    /**
     * Deletes a file given its URL or path.
     * 
     * @param fileUrl The URL or path of the file to delete.
     * @return True if deletion was successful.
     */
    boolean deleteFile(String fileUrl);
}
