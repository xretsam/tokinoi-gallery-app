package ru.meinone.tokinoi_gallery_app.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import ru.meinone.tokinoi_gallery_app.model.Commentary;
import ru.meinone.tokinoi_gallery_app.model.Gallery;

import java.util.Date;
import java.util.List;

public class ImageDTO {
    private int id;
    private String url;
    private Gallery gallery;
    private List<Integer> commentaries;
    private Date createdAt;
}
