package ru.meinone.tokinoi_gallery_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.meinone.tokinoi_gallery_app.enums.Authority;
import ru.meinone.tokinoi_gallery_app.model.Gallery;
import ru.meinone.tokinoi_gallery_app.model.User;
import ru.meinone.tokinoi_gallery_app.repository.GalleryRepository;
import ru.meinone.tokinoi_gallery_app.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final GalleryRepository galleryRepository;
    private final UserRepository userRepository;
    // TODO: provide a better check for authorization
    public void checkGalleryOwnership(Gallery gallery) {
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals(gallery.getAuthor().getUsername())
                && !SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Authority.READ_ALL.getAuthority()))) {
            throw new AuthorizationDeniedException("The ownership check is failed", () -> false);
        }
    }
    public void checkUser(User user) {
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals(user.getUsername())
                && !SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Authority.DELETE_ALL.getAuthority()))) {
            throw new AuthorizationDeniedException("The user is not authorized", () -> false);
        }
    }
}
