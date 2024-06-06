package ru.meinone.tokinoi_gallery_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.meinone.tokinoi_gallery_app.dto.GalleryDTO;
import ru.meinone.tokinoi_gallery_app.model.Gallery;
import ru.meinone.tokinoi_gallery_app.repository.GalleryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GalleryService {
    private final GalleryRepository galleryRepository;
    public List<GalleryDTO> searchGalleriesByTitle(String title) {
        List<Gallery> galleries = galleryRepository.findByTitleContainingIgnoreCase(title);
        System.out.println(galleries.get(0).getAuthor().getUsername());
        return galleries.stream().map(GalleryDTO::new).toList();
    }
    public Optional<GalleryDTO> searchGalleryById(int id) {
        return galleryRepository.findById(id).map(GalleryDTO::new);
    }
}
