package ru.orlov.jwtauthapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.orlov.jwtauthapp.model.User;
import ru.orlov.jwtauthapp.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
