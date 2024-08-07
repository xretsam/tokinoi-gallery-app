package ru.meinone.tokinoi_gallery_app.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.meinone.tokinoi_gallery_app.dto.ImageRequestDTO;
import ru.meinone.tokinoi_gallery_app.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<?> addImage(@ModelAttribute @Validated ImageRequestDTO imageRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream().map(objectError -> "Error: " + objectError.getDefaultMessage()).toList());
        }
        try {
            imageService.uploadImage(imageRequest.getImage(), imageRequest.getGallery_id());
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save image");
        }
    }

    @GetMapping("/**")
    public ResponseEntity<Resource> getImage(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        String imageUrl = requestUrl.substring("/api/image/uploads/".length());
        System.out.println(imageUrl);
        try {
            Pair<Resource, String> resourceAndContentType = imageService.getImage(imageUrl);
            Resource resource = resourceAndContentType.getFirst();
            String contentType = resourceAndContentType.getSecond();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
