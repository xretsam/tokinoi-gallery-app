package ru.meinone.tokinoi_gallery_app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secret;
    private final int expirationTime;

    public JwtTokenProvider(@Value("${spring.jwt.secret}") String secret,
                            @Value("${spring.jwt.expiration-time}") int expirationTime) {
        this.secret = secret;
        this.expirationTime = expirationTime;
    }


    public String generateToken(String username, Date now) {
        Date expirationDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(username)
                .claim("username", username)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
