package ru.meinone.tokinoi_gallery_app.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import ru.meinone.tokinoi_gallery_app.util.validation.constraint.ImageContentConstraint;

@Data
public class ImageRequestDTO {
    @NotNull(message = "The gallery id is not specified")
    private Integer gallery_id;
    @NotNull(message = "Image is empty")
    @ImageContentConstraint
    private MultipartFile image;
}
