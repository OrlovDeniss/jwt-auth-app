package ru.orlov.jwtauthapp.security.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import ru.orlov.jwtauthapp.model.User;
import ru.orlov.jwtauthapp.repository.UserRepository;
import ru.orlov.jwtauthapp.util.UserTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSecDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserSecDetailsService userSecDetailsService;

    @Test
    void loadUserByUsernameTest() {
        String username = "username";
        User user = UserTestUtils.createUser();

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.ofNullable(user));

        UserDetails userDetails = userSecDetailsService.loadUserByUsername(username);

        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());

        verify(userRepository, times(1))
                .findByUsername(username);
    }
}
