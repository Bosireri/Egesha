package com.eParking.Egesha.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageUploadService {

    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFileName = generateUniqueFileName(fileExtension);
        String storageLocation = "C:\\Users\\nyabu\\Desktop\\Egesha\\Images\\";
        String filePath = storageLocation + uniqueFileName;

        Path destinationPath = Path.of(filePath);
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        return filePath;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private String generateUniqueFileName(String fileExtension) {
        String uniqueFileName = UUID.randomUUID().toString();
        if (!fileExtension.isEmpty()) {
            uniqueFileName += "." + fileExtension;
        }
        return uniqueFileName;
    }

}
