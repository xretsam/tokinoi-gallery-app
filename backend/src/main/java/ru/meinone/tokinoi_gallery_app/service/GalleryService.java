package ru.meinone.tokinoi_gallery_app.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.meinone.tokinoi_gallery_app.dto.GalleryEditRequestDTO;
import ru.meinone.tokinoi_gallery_app.enums.GalleryStatus;
import ru.meinone.tokinoi_gallery_app.model.Gallery;
import ru.meinone.tokinoi_gallery_app.model.Tag;
import ru.meinone.tokinoi_gallery_app.model.document.GalleryDoc;
import ru.meinone.tokinoi_gallery_app.repository.CategoryRepository;
import ru.meinone.tokinoi_gallery_app.repository.ElasticRepository;
import ru.meinone.tokinoi_gallery_app.repository.GalleryRepository;
import ru.meinone.tokinoi_gallery_app.repository.TagRepository;
import ru.meinone.tokinoi_gallery_app.security.UserDetailsImpl;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GalleryService {
    private final TagRepository tagRepository;
    private final GalleryRepository galleryRepository;
    private final FileStorageService fileStorageService;
    private final CategoryRepository categoryRepository;
    private final AuthorizationService authorizationService;
    private final ElasticRepository elasticRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public List<Gallery> searchGalleriesByTitle(String title, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GalleryDoc> galleryPage = elasticRepository.findByTitleAndStatus(title, "ACTIVE",pageable);
        return getGalleriesFromPage(galleryPage);
    }

    public List<Gallery> searchGalleries(String title, String author, List<String> tags, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Query nquery = NativeQuery.builder()
                .withQuery( q ->
                        q.bool( b -> {
                            if(!title.isEmpty()) {
                                b.must( m ->
                                        m.match( ma ->
                                                ma.field("title")
                                                        .query(title)
                                        ));
                            }
                            if(!author.isEmpty()) {
                                b.must( m ->
                                        m.term( t ->
                                                t.field("author")
                                                        .value(author)
                                        ));
                            }
                            if(!tags.isEmpty()) {
                                for(String tag : tags) {
                                    b.must( m ->
                                            m.term( t ->
                                                    t.field("tags")
                                                            .value(tag))
                                    );
                                }
                            }
                            return b;
                                }
                        )
                )
                .withPageable(pageable)
                .build();

        return  elasticsearchOperations.search(nquery, GalleryDoc.class, IndexCoordinates.of("gallery_index")).stream()
                .map(searchHit -> {
                    System.out.println(searchHit.getScore() + " " + searchHit.getContent());
                   return searchHit.getContent();
                })
                .map(galleryDoc -> galleryRepository.findById(galleryDoc.getGalleryId()))
                .filter(gallery -> gallery.isPresent())
                .map(gallery -> gallery.get())
                .toList();
    }

    private List<Gallery> getGalleriesFromPage(Page<GalleryDoc> galleryPage) {
        if (galleryPage.hasContent()) {
            return galleryPage.stream()
                    .map(galleryDoc -> {
                        System.out.println(galleryDoc.getTitle() + " " + galleryDoc.getGalleryId());
                        return galleryRepository.findById(galleryDoc.getGalleryId());
                    })//TODO: May impact performance, consider reducing the number of requests
                    .filter(galleryOpt -> galleryOpt.isPresent())
                    .map(galleryOpt -> galleryOpt.get())
                    .toList();
        }
        return Collections.emptyList();
    }

    @PostAuthorize("(returnObject.get().author.username.equals(authentication.name) " +
            "|| hasAuthority('read_all'))" +
            "&& !returnObject.get().status.toString().equals('ACTIVE')" +
            "|| returnObject.get().status.toString().equals('ACTIVE')")
    public Optional<Gallery> searchGalleryById(int id) {
        Optional<Gallery> gallery = galleryRepository.findById(id);
        return gallery;
    }

    @PreAuthorize("!authentication.name.equals('anonymousUser')")
    public Integer saveGallery(Gallery gallery) {
        Date now = new Date();
        gallery.setTitle(now.toString());
        gallery.setCreatedAt(now);
        gallery.setUpdatedAt(now);
        gallery.setAuthor(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());
        gallery.setStatus(GalleryStatus.INACTIVE);
        Gallery savedGallery = galleryRepository.save(gallery);
        GalleryDoc galleryDoc = new GalleryDoc();
        elasticRepository.save(galleryDoc.setAttributes(gallery));
        return savedGallery.getId();
    }

    @PreAuthorize("!authentication.name.equals('anonymousUser')")
    public void updateGallery(Integer id, GalleryEditRequestDTO updateDTO) throws IOException {
        Gallery gallery = galleryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Gallery not found"));
        GalleryDoc galleryDoc = elasticRepository.findByGalleryId(gallery.getId());
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
            List<Tag> tags = updateDTO.getTags().stream()
                    .map(this::addTag)
                    .filter(Objects::nonNull)
                    .toList();
            tags.forEach(tag -> gallery.getTags().add(tag));
            System.out.println("tags not null");
            gallery.getTags().forEach(galleryTag -> System.out.println(galleryTag.getTag()));
        }
        if (updateDTO.getCategory() != null) {
            setCategory(gallery, updateDTO.getCategory());
        }
        galleryDoc.setAttributes(gallery);
        System.out.println(updateDTO.getTitle());
        System.out.println(gallery.getTitle());
        System.out.println(galleryDoc.getTitle());
        elasticRepository.save(galleryDoc);
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

    @PreAuthorize("!authentication.name.equals('anonymousUser')")
    public void publishGallery(Integer id) {
        Gallery gallery = galleryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Gallery not found"));
        GalleryDoc galleryDoc = elasticRepository.findByGalleryId(gallery.getId());
        authorizationService.checkGalleryOwnership(gallery);
        gallery.setStatus(GalleryStatus.ACTIVE);
        elasticRepository.save(galleryDoc.setAttributes(gallery));
        galleryRepository.save(gallery);
    }

    @PreAuthorize("!authentication.name.equals('anonymousUser')")
    public void privatizeGallery(Integer id) {
        Gallery gallery = galleryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Gallery not found"));
        authorizationService.checkGalleryOwnership(gallery);
        gallery.setStatus(GalleryStatus.PRIVATE);
        galleryRepository.save(gallery);
    }

    @PreAuthorize("!authentication.name.equals('anonymousUser')")
    public void deleteGallery(Integer id) {
        Gallery gallery = galleryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Gallery not found"));
        authorizationService.checkGalleryOwnership(gallery);
        gallery.setStatus(GalleryStatus.DELETED);
        galleryRepository.save(gallery);
    }
}
