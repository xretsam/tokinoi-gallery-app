package ru.meinone.tokinoi_gallery_app.repository;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.meinone.tokinoi_gallery_app.model.document.Gallery;


@Repository
public interface ElasticRepository extends ElasticsearchRepository<Gallery,Long> {
    SearchHits<Gallery> findByTitle(String title);
}
