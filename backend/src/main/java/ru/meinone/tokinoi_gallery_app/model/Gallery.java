package ru.meinone.tokinoi_gallery_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.meinone.tokinoi_gallery_app.enums.GalleryStatus;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "gallery")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GalleryStatus status;
    @Column(name = "thumbnail")
    private String thumbnail;
    @OneToMany(mappedBy = "gallery")
    private List<Image> images;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @OneToMany(mappedBy = "gallery")
    private List<Commentary> commentaries;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToMany
    @JoinTable(
            name = "gallery_tag",
            joinColumns = @JoinColumn(name = "gallery_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}
