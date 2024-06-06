package ru.meinone.tokinoi_gallery_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.meinone.tokinoi_gallery_app.dto.GalleryDTO;
import ru.meinone.tokinoi_gallery_app.model.Gallery;
import ru.meinone.tokinoi_gallery_app.service.GalleryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
public class GalleryController {
    private final GalleryService galleryService;

    @GetMapping("/{id}")
    public ResponseEntity<GalleryDTO> getGallery(@PathVariable int id) {
        return galleryService.searchGalleryById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
    @GetMapping("/search")
    public List<GalleryDTO> searchGallery(@RequestParam(name = "name") String title) {
        return galleryService.searchGalleriesByTitle(title);
    }
}
