package ru.meinone.tokinoi_gallery_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.meinone.tokinoi_gallery_app.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<String> addImage(@RequestParam("file") MultipartFile file, @RequestParam("gallery_id") int galleryId) {
        try {
            imageService.uploadImage(file, galleryId);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save image");
        }
    }
}
