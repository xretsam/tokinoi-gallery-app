package ru.meinone.tokinoi_gallery_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.meinone.tokinoi_gallery_app.dto.GalleryRequestDTO;
import ru.meinone.tokinoi_gallery_app.dto.GalleryResponseDTO;
import ru.meinone.tokinoi_gallery_app.model.Gallery;
import ru.meinone.tokinoi_gallery_app.model.User;
import ru.meinone.tokinoi_gallery_app.security.UserDetailsImpl;
import ru.meinone.tokinoi_gallery_app.service.GalleryService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
public class GalleryController {
    private final GalleryService galleryService;

    @GetMapping("/{id}")
    public ResponseEntity<GalleryResponseDTO> getGallery(@PathVariable int id) {
        return galleryService.searchGalleryById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public List<GalleryResponseDTO> searchGallery(@RequestParam(name = "name") String title) {
        return galleryService.searchGalleriesByTitle(title);
    }

    @PostMapping
    public ResponseEntity<?> createGallery() {
        Integer id = galleryService.saveGallery(new Gallery());
        return ResponseEntity.ok(Collections.singletonMap("galleryId", id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateGallery(@ModelAttribute @Validated GalleryRequestDTO requestDTO,
                                           BindingResult bindingResult,
                                           @PathVariable Integer id) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            galleryService.updateGallery(id, requestDTO);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
        return ResponseEntity.ok("Gallery updated");
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<?> publishGallery(@PathVariable Integer id) {
        galleryService.publishGallery(id);
        return ResponseEntity.ok("Gallery published");
    }

    @PostMapping("/{id}/privatize")
    public ResponseEntity<?> privatizeGallery(@PathVariable Integer id) {
        galleryService.privatizeGallery(id);
        return ResponseEntity.ok("Gallery is private");
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> deleteGallery(@PathVariable Integer id) {
        galleryService.deleteGallery(id);
        return ResponseEntity.ok("Gallery deleted");
    }

}
