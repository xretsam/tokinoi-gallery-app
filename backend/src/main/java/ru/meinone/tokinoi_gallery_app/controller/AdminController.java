package ru.meinone.tokinoi_gallery_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.meinone.tokinoi_gallery_app.service.UserService;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    @PostMapping("/user/{id}/ban")
    public ResponseEntity<?> banUser(@PathVariable Integer id) {
        userService.banUser(id);
        return ResponseEntity.ok("User has been banned");
    }
    @PostMapping("/user/{id}/unban")
    public ResponseEntity<?> unbanUser(@PathVariable Integer id) {
        userService.unbanUser(id);
        return ResponseEntity.ok("User has been unbanned");
    }
    @PostMapping("/user/{id}/makeUser")
    public ResponseEntity<?> makeUser(@PathVariable Integer id) {
        userService.makeUser(id);
        return ResponseEntity.ok("User is now simple user");
    }
    @PostMapping("/user/{id}/makeModerator")
    public ResponseEntity<?> makeModerator(@PathVariable Integer id) {
        userService.makeModerator(id);
        return ResponseEntity.ok("User is now moderator");
    }
    @PostMapping("/user/{id}/makeAdmin")
    public ResponseEntity<?> makeAdmin(@PathVariable Integer id) {
        userService.makeAdmin(id);
        return ResponseEntity.ok("User is now admin");
    }
}
