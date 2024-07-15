package ru.meinone.tokinoi_gallery_app.enums;

import lombok.Getter;

@Getter
public enum Authority {
    COMMENT("comment"),
    PUBLISH_GALLERY("publish_gallery"),
    BAN("ban"),
    UNBAN("unban"),
    CHANGE_ROLE("change_role"),
    READ_ALL("read_all"),
    DELETE_ALL("delete_all");
    private final String authority;
    Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return this.authority;
    }
}
