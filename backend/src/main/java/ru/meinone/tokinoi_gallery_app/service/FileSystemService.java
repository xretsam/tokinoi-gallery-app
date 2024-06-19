package ru.meinone.tokinoi_gallery_app.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileSystemService {
    String createFile(MultipartFile file, String... path) throws IOException;
    Resource readFile(String fileUrl) throws IOException;
    boolean deleteFile(String Path) throws IOException;
}
