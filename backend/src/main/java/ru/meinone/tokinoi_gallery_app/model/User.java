package ru.meinone.tokinoi_gallery_app.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
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
    private String role;

    @Column(name = "status")
    private String status;

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
}
