package ru.meinone.tokinoi_gallery_app.enums;

import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Role {
    USER(Set.of(Authority.COMMENT, Authority.PUBLISH_GALLERY)),
    MODERATOR(Stream.concat(USER.getAuthorities().stream(),
                            Stream.of(Authority.BAN, Authority.UNBAN))
                    .collect(Collectors.toSet())),
    ADMIN(Stream.concat(MODERATOR.getAuthorities().stream(),
                        Stream.of(Authority.CHANGE_ROLE))
                .collect(Collectors.toSet()));

    private final Set<Authority> authorities;

    Role(Set<Authority> authorities) {
        this.authorities = authorities;
    }

}
