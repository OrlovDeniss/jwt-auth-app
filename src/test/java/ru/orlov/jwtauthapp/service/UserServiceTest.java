package ru.orlov.jwtauthapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.orlov.jwtauthapp.model.User;
import ru.orlov.jwtauthapp.repository.UserRepository;
import ru.orlov.jwtauthapp.util.UserTestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void saveUserTest() {
        User user = UserTestUtils.createUser();
        userService.saveUser(user);
        verify(userRepository, times(1))
                .save(user);
    }

    @Test
    void existsByUsernameTest() {
        when(userRepository.existsByUsername(anyString()))
                .thenReturn(true);
        assertTrue(userService.existsUserByUsername("username"));
        verify(userRepository, times(1))
                .existsByUsername(anyString());
    }
}
