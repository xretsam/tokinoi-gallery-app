package ru.meinone.tokinoi_gallery_app.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.meinone.tokinoi_gallery_app.dto.UserDTO;
import ru.meinone.tokinoi_gallery_app.enums.Role;
import ru.meinone.tokinoi_gallery_app.enums.UserStatus;
import ru.meinone.tokinoi_gallery_app.model.User;
import ru.meinone.tokinoi_gallery_app.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;
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

    @PreAuthorize("!authentication.name.equals('anonymousUser')")
    public void delete(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        authorizationService.checkUser(user);
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    @PreAuthorize("hasAuthority('ban')")
    public void banUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setStatus(UserStatus.BANNED);
        userRepository.save(user);
    }
    @PreAuthorize("hasAuthority('unban')")
    public void unbanUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }
    @PreAuthorize("hasAuthority('change_role')")
    public void makeUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setRole(Role.USER);
        userRepository.save(user);
    }
    @PreAuthorize("hasAuthority('change_role')")
    public void makeModerator(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setRole(Role.MODERATOR);
        userRepository.save(user);
    }
    @PreAuthorize("hasAuthority('change_role')")
    public void makeAdmin(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }
}
