package ru.orlov.jwtauthapp.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.orlov.jwtauthapp.model.User;
import ru.orlov.jwtauthapp.repository.UserRepository;
import ru.orlov.jwtauthapp.security.model.UserSecDetails;

@Service
@RequiredArgsConstructor
public class UserSecDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не существует."));
        return new UserSecDetails(user);
    }
}
