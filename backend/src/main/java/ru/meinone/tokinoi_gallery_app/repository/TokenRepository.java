package ru.meinone.tokinoi_gallery_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meinone.tokinoi_gallery_app.enums.TokenType;
import ru.meinone.tokinoi_gallery_app.model.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    public Optional<Token> findByToken(String token);
    public List<Token> findByUserIdAndRevokedIsAndTypeIs(int user_id, boolean revoked, TokenType type);
}
