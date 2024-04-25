package ru.orlov.jwtauthapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.orlov.jwtauthapp.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
