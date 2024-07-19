package ru.meinone.tokinoi_gallery_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.meinone.tokinoi_gallery_app.dto.GalleryEditRequestDTO;
import ru.meinone.tokinoi_gallery_app.dto.GalleryResponseDTO;
import ru.meinone.tokinoi_gallery_app.dto.GallerySearchDTO;
import ru.meinone.tokinoi_gallery_app.model.Gallery;
import ru.meinone.tokinoi_gallery_app.service.GalleryService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
public class GalleryController {
    private final GalleryService galleryService;

    @GetMapping("/{id}")
    public ResponseEntity<GalleryResponseDTO> getGallery(@PathVariable int id) {
        return galleryService.searchGalleryById(id)
                .map(GalleryResponseDTO::new)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchGallery(@RequestBody GallerySearchDTO searchRequest) {
        System.out.println("title: " + searchRequest.getTitle() + "page: " + searchRequest.getPage() + "page: " + searchRequest.getPageSize());
//        List<Gallery> result = galleryService.searchGalleriesByTitle(searchRequest.getTitle(), searchRequest.getPage(), searchRequest.getPageSize());
        List<Gallery> result = galleryService.searchGalleries(searchRequest.getTitle(), searchRequest.getAuthor(), searchRequest.getTags(), searchRequest.getPage(), searchRequest.getPageSize());

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No galleries found");
        }
        return ResponseEntity.ok(result.stream().map(GalleryResponseDTO::new).toList());
    }

    @PostMapping
    public ResponseEntity<?> createGallery() {
        Integer id = galleryService.saveGallery(new Gallery());
        return ResponseEntity.ok(Collections.singletonMap("galleryId", id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateGallery(@RequestBody @Validated GalleryEditRequestDTO requestDTO,
                                           BindingResult bindingResult,
                                           @PathVariable Integer id) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream().map(objectError -> "Error: " + objectError.getDefaultMessage()).toList());
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
