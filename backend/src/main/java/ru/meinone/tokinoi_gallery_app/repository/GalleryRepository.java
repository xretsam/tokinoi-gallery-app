package ru.meinone.tokinoi_gallery_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meinone.tokinoi_gallery_app.model.Gallery;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
}
