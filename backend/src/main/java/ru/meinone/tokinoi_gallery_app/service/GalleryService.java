package ru.meinone.tokinoi_gallery_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.meinone.tokinoi_gallery_app.model.Gallery;
import ru.meinone.tokinoi_gallery_app.repository.GalleryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryService {
    private final GalleryRepository galleryRepository;
    public List<Gallery> searchGalleriesByTitle(String title) {
        return galleryRepository.findByTitleContainingIgnoreCase(title);
    }
    public Optional<Gallery> searchGalleryById(int id) {
        return galleryRepository.findById(id);
    }
}
