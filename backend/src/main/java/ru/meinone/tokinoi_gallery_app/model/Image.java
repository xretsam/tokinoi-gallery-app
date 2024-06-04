package ru.meinone.tokinoi_gallery_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "url")
    private String url;
    @Column(name = "created_at")
    private Date createdAt;
    @ManyToOne()
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;
    @OneToMany(mappedBy = "image")
    private List<Commentary> commentaries;
}
