package ru.meinone.tokinoi_gallery_app.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.meinone.tokinoi_gallery_app.dto.GalleryRequestDTO;
import ru.meinone.tokinoi_gallery_app.dto.GalleryResponseDTO;
import ru.meinone.tokinoi_gallery_app.enums.GalleryStatus;
import ru.meinone.tokinoi_gallery_app.model.Gallery;
import ru.meinone.tokinoi_gallery_app.model.Tag;
import ru.meinone.tokinoi_gallery_app.repository.CategoryRepository;
import ru.meinone.tokinoi_gallery_app.repository.GalleryRepository;
import ru.meinone.tokinoi_gallery_app.repository.TagRepository;
import ru.meinone.tokinoi_gallery_app.security.UserDetailsImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryService {
    private final TagRepository tagRepository;
    private final GalleryRepository galleryRepository;
    private final FileStorageService fileStorageService;
    private final CategoryRepository categoryRepository;
    public List<GalleryResponseDTO> searchGalleriesByTitle(String title) {
        List<Gallery> galleries = galleryRepository.findByTitleContainingIgnoreCase(title);
        System.out.println(galleries.get(0).getAuthor().getUsername());
        return galleries.stream().map(GalleryResponseDTO::new).toList();
    }
    public Optional<GalleryResponseDTO> searchGalleryById(int id) {
        return galleryRepository.findById(id).map(GalleryResponseDTO::new);
    }
//    public List<GalleryResponseDTO> searchGalleriesByAuthor(String author) {}
    public Integer saveGallery(Gallery gallery) {
        Date now = new Date();
        gallery.setTitle(now.toString());
        gallery.setCreatedAt(now);
        gallery.setUpdatedAt(now);
        gallery.setAuthor(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());
        gallery.setStatus(GalleryStatus.INACTIVE);
        return galleryRepository.save(gallery).getId();
    }

    public void updateGallery(Integer id, GalleryRequestDTO updateDTO) throws IOException {
        Gallery gallery = galleryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Gallery not found"));

        if (updateDTO.getThumbnail() != null) {
            saveThumbnail(gallery, updateDTO.getThumbnail());
        }
        if (updateDTO.getTitle() != null) {
            gallery.setTitle(updateDTO.getTitle());
        }
        if (updateDTO.getDescription() != null) {
            gallery.setDescription(updateDTO.getDescription());
        }
        if (updateDTO.getTags() != null) {
            gallery.setTags(updateDTO.getTags().stream().map(this::addTag).filter(Objects::nonNull).toList());
        }
        if (updateDTO.getCategory() != null) {
            setCategory(gallery, updateDTO.getCategory());
        }

        galleryRepository.save(gallery);
    }
    private void saveThumbnail(Gallery gallery, MultipartFile thumbnail) throws IOException {
        gallery.setThumbnail(fileStorageService.createFile(thumbnail, "gallery", "thumbnail", gallery.getId().toString()));
    }
    private void setCategory(Gallery gallery, String category) {
        if (categoryRepository.findByCategory(category).isPresent()) {
            gallery.setCategory(categoryRepository.findByCategory(category).get());
        } else throw new EntityNotFoundException("Category not found");
    }

    public Tag addTag(String tagName) {
        if (tagName != null && !tagName.trim().isEmpty()) {
            tagName = tagName.toLowerCase();
            Optional<Tag> tag = tagRepository.findByTag(tagName);
            if (tag.isEmpty()) {
                Tag newTag = new Tag();
                newTag.setTag(tagName);
                tag = Optional.of(tagRepository.save(newTag));
            }
            return tag.get();
        }
        return null;
    }
}
