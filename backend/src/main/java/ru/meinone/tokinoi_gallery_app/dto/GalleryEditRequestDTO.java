package ru.meinone.tokinoi_gallery_app.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import ru.meinone.tokinoi_gallery_app.util.validation.constraint.ImageContentConstraint;

import java.util.List;

@Data
public class GalleryEditRequestDTO {
    @ImageContentConstraint
    private MultipartFile thumbnail;

    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    private String title;

    @Size(max = 5000, message = "Description cannot be longer than 5000 characters")
    private String description;

    private List<String> tags;

    private String category;
}
