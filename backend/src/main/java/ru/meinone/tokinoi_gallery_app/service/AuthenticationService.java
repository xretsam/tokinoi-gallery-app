package ru.meinone.tokinoi_gallery_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.meinone.tokinoi_gallery_app.enums.Role;
import ru.meinone.tokinoi_gallery_app.enums.UserStatus;
import ru.meinone.tokinoi_gallery_app.model.Token;
import ru.meinone.tokinoi_gallery_app.model.User;
import ru.meinone.tokinoi_gallery_app.security.UserDetailsImpl;
import ru.meinone.tokinoi_gallery_app.util.exception.AuthenticationException;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public String register(String username, String email, String password) {
        if(userService.getUserByEmail(email).isPresent()) {
            throw new AuthenticationException("email already in use");
        } else if(userService.getUserByUsername(username).isPresent()) {
            throw new AuthenticationException("username already in use");
        }
        Date now = new Date();
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();
        userService.save(user);
        Token jwt = tokenService.createToken(user);
        return jwt.getToken();
    }
    public String login(String login, String password) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(login, password);
        this.authenticationManager.authenticate(authenticationRequest);
        Optional<User> user = userService.getUserByUsername(login);
        if (user.isPresent()) {
            Token jwt = tokenService.createToken(user.get());
            return jwt.getToken();
        }
        SecurityContextHolder.clearContext();
        throw new AuthenticationException("incorrect login or password");
    }
    public void logout(String token) {
        tokenService.revokeToken(token);
    }
    public void logoutAll(User user) {
        tokenService.revokeTokens(user);
    }
}
