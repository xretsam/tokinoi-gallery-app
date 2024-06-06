package ru.meinone.tokinoi_gallery_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meinone.tokinoi_gallery_app.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findById(int id);
    public Optional<User> findByUsername(String username);
}
