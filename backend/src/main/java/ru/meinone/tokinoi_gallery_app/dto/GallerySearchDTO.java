package ru.meinone.tokinoi_gallery_app.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GallerySearchDTO {
    private String title = "";
    private String author = "";
    private List<String> tags = new ArrayList<>();
    private String category = "";
    private Integer page = 0;
    private Integer pageSize = 10;
    private String sortBy;
}
