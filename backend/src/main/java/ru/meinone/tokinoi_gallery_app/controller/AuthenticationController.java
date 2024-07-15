package ru.meinone.tokinoi_gallery_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.meinone.tokinoi_gallery_app.dto.LoginRequestDTO;
import ru.meinone.tokinoi_gallery_app.dto.RegisterRequestDTO;
import ru.meinone.tokinoi_gallery_app.enums.UserStatus;
import ru.meinone.tokinoi_gallery_app.model.Token;
import ru.meinone.tokinoi_gallery_app.model.User;
import ru.meinone.tokinoi_gallery_app.enums.Role;
import ru.meinone.tokinoi_gallery_app.security.UserDetailsImpl;
import ru.meinone.tokinoi_gallery_app.service.TokenService;
import ru.meinone.tokinoi_gallery_app.service.UserService;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO registerRequest) {
        if (userService.getUserByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User with this email already exists");
        } else if (userService.getUserByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User with this username already exists");
        }
        Date createdAt = new Date();
        User newUser = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdAt(createdAt)
                .updatedAt(createdAt)
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
        userService.save(newUser);
        Token jwt = tokenService.createToken(newUser);
        return ResponseEntity.ok(Map.of("jwt", jwt.getToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
        this.authenticationManager.authenticate(authenticationRequest);
        Optional<User> user = userService.getUserByUsername(loginRequest.getUsername());
        if (user.isPresent()) {
            Token jwt = tokenService.createToken(user.get());
            return ResponseEntity.ok(Map.of("jwt", jwt.getToken()));
        }
        return ResponseEntity.badRequest().body("Invalid username or password");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userDetails.getUsername();
        userService.getUserByUsername(userDetails.getUsername()).ifPresent(tokenService::revokeTokens);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Successfully logged out");
    }
}
