package ru.meinone.tokinoi_gallery_app.model;

import jakarta.persistence.*;
import lombok.*;
import ru.meinone.tokinoi_gallery_app.enums.Role;
import ru.meinone.tokinoi_gallery_app.enums.UserStatus;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "last_login")
    private Date lastLogin;
    @OneToMany(mappedBy = "author")
    private List<Gallery> galleries;
    @OneToMany(mappedBy = "author")
    private List<Commentary> commentaries;
    @OneToMany(mappedBy = "subscriber")
    private List<Subscription> subscriptions;
    @OneToMany(mappedBy = "targetUser")
    private List<Subscription> subscribers;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
}
