package ru.meinone.tokinoi_gallery_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.meinone.tokinoi_gallery_app.enums.TokenType;
import ru.meinone.tokinoi_gallery_app.model.Token;
import ru.meinone.tokinoi_gallery_app.model.User;
import ru.meinone.tokinoi_gallery_app.repository.TokenRepository;
import ru.meinone.tokinoi_gallery_app.repository.UserRepository;
import ru.meinone.tokinoi_gallery_app.security.JwtTokenProvider;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    @Value("${spring.jwt.expiration-time}")
    private int expirationTime;

    public Token createToken(User user) {
        Date now = new Date();
        String jwt = jwtTokenProvider.generateToken(user.getUsername(), now);
        Token token = new Token();
        token.setRevoked(false);
        token.setToken(jwt);
        token.setUser(user);
        token.setCreatedAt(now);
        token.setExpiresAt(new Date(now.getTime() + expirationTime));
        token.setType(TokenType.JWT);
        tokenRepository.save(token);
        return token;
    }

    public void revokeTokens(User user) {
        tokenRepository
                .findByUserIdAndRevokedIsAndTypeIs(user.getId(), false, TokenType.JWT)
                .forEach(token -> {
                    token.setRevoked(true);
                    tokenRepository.save(token);
                });
    }
    public void revokeToken(Token token) {
        tokenRepository
                .findByToken(token.getToken()).ifPresent(token1 -> token1.setRevoked(true));
    }

    public boolean isTokenValid(String token, TokenType type) {
        if (Objects.requireNonNull(type) == TokenType.JWT) {
            if(jwtTokenProvider.validateToken(token)) {
                Optional<Token> tokenOptional = tokenRepository.findByToken(token);
                if(tokenOptional.isPresent()) {
                    return !tokenOptional.get().isRevoked();
                }
            }
        }
        return false;
    }
}
