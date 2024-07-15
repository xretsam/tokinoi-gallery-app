package ru.meinone.tokinoi_gallery_app.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.meinone.tokinoi_gallery_app.model.Gallery;
import ru.meinone.tokinoi_gallery_app.model.Image;
import ru.meinone.tokinoi_gallery_app.repository.GalleryRepository;
import ru.meinone.tokinoi_gallery_app.repository.JpaImageRepository;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final JpaImageRepository jpaImageRepository;
    private final GalleryRepository galleryRepository;
    private final AuthorizationService authorizationService;
    private final FileStorageService fileStorageService;
    public void uploadImage(MultipartFile file, Integer galleryId) throws IOException {
        System.out.println(file.getContentType());
        Gallery gallery = galleryRepository.findById(galleryId).orElseThrow(() -> new EntityNotFoundException("Gallery not found"));
        authorizationService.checkGalleryOwnership(gallery);
        String url = fileStorageService.createFile(file, "gallery", String.valueOf(galleryId));
        System.out.println(url);
        Image image = new Image();
        image.setGallery(gallery);
        image.setUrl(url);
        image.setCreatedAt(new Date());
        jpaImageRepository.save(image);
    }
    public Pair<Resource, String> getImage(String imageUrl) throws IOException {
        return fileStorageService.readFile(imageUrl);
    }

}
