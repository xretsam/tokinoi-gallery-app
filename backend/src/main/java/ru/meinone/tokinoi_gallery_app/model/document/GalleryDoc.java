package ru.meinone.tokinoi_gallery_app.model.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import ru.meinone.tokinoi_gallery_app.model.Gallery;
import ru.meinone.tokinoi_gallery_app.model.Tag;

import java.util.Date;
import java.util.List;

@Document(indexName = "gallery_index")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GalleryDoc {
    @Id
    private String id;
    private Integer galleryId;
    private String title;
    private String description;
    private String author;
    private Date createdAt;
    private Date updatedAt;
    private String status;
    private List<String> tags;

    public GalleryDoc setAttributes(Gallery gallery) {
        this.galleryId = gallery.getId();
        this.title = gallery.getTitle();
        this.description = gallery.getDescription();
        this.author = gallery.getAuthor().getUsername();
        this.createdAt = gallery.getCreatedAt();
        this.updatedAt = gallery.getUpdatedAt();
        this.status = gallery.getStatus().name();
        if (gallery.getTags() != null) {
            this.tags = gallery.getTags()
                    .stream()
                    .map(Tag::getTag)
                    .toList();
        }
        return this;
    }
}
