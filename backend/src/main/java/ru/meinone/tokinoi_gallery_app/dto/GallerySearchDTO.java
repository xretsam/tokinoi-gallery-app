package ru.meinone.tokinoi_gallery_app.dto;

import lombok.Data;

import java.util.List;

@Data
public class GallerySearchDTO {
    private String title;
    private String author;
    private List<String> tags;
    private String category;
    private Integer page;
    private Integer pageSize;
    private String sortBy;
}
