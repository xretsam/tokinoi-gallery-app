package ru.meinone.tokinoi_gallery_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.meinone.tokinoi_gallery_app.dto.UserDTO;
import ru.meinone.tokinoi_gallery_app.model.User;
import ru.meinone.tokinoi_gallery_app.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        return userService.getUserById(id)
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<UserDTO> getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username)
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null,HttpStatus.NOT_FOUND));
    }
}
