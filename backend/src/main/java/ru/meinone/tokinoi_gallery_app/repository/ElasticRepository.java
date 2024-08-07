package ru.meinone.tokinoi_gallery_app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.meinone.tokinoi_gallery_app.model.document.GalleryDoc;

import java.util.Collection;
import java.util.List;


@Repository
public interface ElasticRepository extends ElasticsearchRepository<GalleryDoc, Long> {
    SearchHits<GalleryDoc> findByTitle(String title);

    Page<GalleryDoc> findByTitleAndStatus(String title, String status, Pageable pageable);

    GalleryDoc findByGalleryId(Integer galleryId);
}
