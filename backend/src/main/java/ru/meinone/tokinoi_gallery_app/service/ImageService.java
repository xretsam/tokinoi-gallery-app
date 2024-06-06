package ru.meinone.tokinoi_gallery_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.meinone.tokinoi_gallery_app.model.Image;
import ru.meinone.tokinoi_gallery_app.repository.GalleryRepository;
import ru.meinone.tokinoi_gallery_app.repository.ImageRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final GalleryRepository galleryRepository;
    @Value("${file.upload-path}")
    private String uploadDir;
    public void uploadImage(MultipartFile file, int galleryId) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        long creationTime = System.currentTimeMillis();
        String fileName = galleryId + "_" + creationTime;
        Path imagePath = uploadPath.resolve(fileName);
        Files.write(imagePath, file.getBytes());

        Image image = new Image();
        image.setGallery(galleryRepository.findById(galleryId).get());
        image.setUrl(fileName);
        image.setCreatedAt(new Date(creationTime));
        imageRepository.save(image);
    }
}
