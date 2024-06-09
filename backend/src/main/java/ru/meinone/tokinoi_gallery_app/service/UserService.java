package ru.meinone.tokinoi_gallery_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.meinone.tokinoi_gallery_app.dto.UserDTO;
import ru.meinone.tokinoi_gallery_app.model.User;
import ru.meinone.tokinoi_gallery_app.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
