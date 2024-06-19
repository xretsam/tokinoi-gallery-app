package ru.meinone.tokinoi_gallery_app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.meinone.tokinoi_gallery_app.model.*;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
public class GalleryResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private Date createdAt;
    private String thumbnail;
    private List<String> images;
    private Category category;
    private List<String> tags;
    private String author;
//    private List<Integer> commentaries;
//    private Date updatedAt;
//    private String status;

    public GalleryResponseDTO(Gallery gallery) {
        this.id = gallery.getId();
        this.title = gallery.getTitle();
        this.description = gallery.getDescription();
        this.createdAt = gallery.getCreatedAt();
        this.thumbnail = gallery.getThumbnail();
        this.category = gallery.getCategory();
        this.images = gallery.getImages().stream().map(Image::getUrl).toList();
        this.tags = gallery.getTags().stream().map(Tag::getTag).toList();
        this.author = gallery.getAuthor().getUsername();
    }
}
