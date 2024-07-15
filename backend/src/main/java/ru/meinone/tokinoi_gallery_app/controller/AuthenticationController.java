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
import ru.meinone.tokinoi_gallery_app.service.AuthenticationService;
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

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO registerRequest) {
        String token = authenticationService.register(registerRequest.getUsername(),registerRequest.getEmail(),registerRequest.getPassword());
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        String token = authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(String token) {
        authenticationService.logout(token);
        return ResponseEntity.ok("logged out");
    }
}
