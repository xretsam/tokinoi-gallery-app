package ru.meinone.tokinoi_gallery_app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.meinone.tokinoi_gallery_app.model.Subscription;
import ru.meinone.tokinoi_gallery_app.model.User;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String username;
    private String profilePicture;
    private String email;
    private String phoneNumber;
    private String role;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private int subscriptionsCount;
    private int subscribersCount;
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.profilePicture = user.getProfilePicture();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole().name();
        this.status = user.getStatus().name();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        subscribersCount = user.getSubscribers().size();
        subscriptionsCount = user.getSubscriptions().size();
    }
}
