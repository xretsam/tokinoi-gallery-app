package ru.meinone.tokinoi_gallery_app.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileSystemServiceImpl implements FileSystemService {
    @Value("${file.upload-path}")
    private String uploadDir;
    @PostConstruct
    public void init() throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    }
    @Override
    public String createFile(MultipartFile file, String... pathParts) throws IOException {
        Path uploadPath = Paths.get(uploadDir, pathParts);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        long creationTime = System.currentTimeMillis();
        String fileName = getFileName(file) + "-"+ creationTime + "." + getFileExtension(file) ;
        Path path = uploadPath.resolve(fileName);
        try {
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path.toString();
    }

    @Override
    public Resource readFile(String fileUrl) throws IOException {
        System.out.println(fileUrl);
        Path fullPath = Paths.get(uploadDir, fileUrl).normalize();
        System.out.println(fullPath.toString());
        Resource resource = new UrlResource(fullPath.toUri());
        System.out.println(resource.getURI());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new IOException("Could not read file: " + fileUrl);
        }
    }

    @Override
    public boolean deleteFile(String fileUrl) throws IOException {
        Path fullPath = Paths.get(uploadDir, fileUrl).normalize();
        return Files.deleteIfExists(fullPath);
    }
    public String getContentType(String fileUrl) throws IOException {
        Path imagePath = Paths.get(uploadDir, fileUrl).normalize();
        return Files.probeContentType(imagePath);
    }
    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.lastIndexOf(".") == -1) {
            return null;
        }
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }
    private String getFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if(originalFilename == null) {
            return null;
        }
        originalFilename = originalFilename.replaceAll("\\s", "");
        if (originalFilename.lastIndexOf(".") == -1) {
            return originalFilename;
        }
        return originalFilename.substring(0, originalFilename.lastIndexOf("."));
    }
}
