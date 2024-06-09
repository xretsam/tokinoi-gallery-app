package ru.meinone.tokinoi_gallery_app.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.meinone.tokinoi_gallery_app.enums.TokenType;

import java.util.Date;

@Entity
@Table(name = "token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "expires_at")
    private Date expiresAt;
    @Column(name = "revoked")
    private boolean revoked;
    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType type;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
