package ru.orlov.jwtauthapp.security.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.orlov.jwtauthapp.model.User;
import ru.orlov.jwtauthapp.security.model.UserSecDetails;
import ru.orlov.jwtauthapp.security.model.dto.JwtAuthenticationResponse;
import ru.orlov.jwtauthapp.security.model.dto.SignInRequest;
import ru.orlov.jwtauthapp.security.model.dto.SignUpRequest;
import ru.orlov.jwtauthapp.util.AuthTestUtils;
import ru.orlov.jwtauthapp.util.UserTestUtils;
import ru.orlov.jwtauthapp.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private UserSecDetailsService userSecDetailsService;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void signUpTest() {
        SignUpRequest signUpRequest = AuthTestUtils.createSignUpRequest();
        String token = AuthTestUtils.createToken();

        when(userService.existsUserByUsername(signUpRequest.getUsername()))
                .thenReturn(false);
        when(jwtService.generateToken(any(UserDetails.class)))
                .thenReturn(token);

        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signUp(signUpRequest);

        assertEquals(token, jwtAuthenticationResponse.getToken());

        verify(userService, times(1))
                .saveUser(any(User.class));
        verify(jwtService, times(1))
                .generateToken(any(UserDetails.class));
    }

    @Test
    void signInTest() {
        User user = UserTestUtils.createUser();
        UserDetails userDetails = new UserSecDetails(user);
        SignInRequest signInRequest = AuthTestUtils.createSignInRequest();
        String username = signInRequest.getUsername();
        String token = AuthTestUtils.createToken();

        when(userSecDetailsService.loadUserByUsername(username))
                .thenReturn(userDetails);
        when(jwtService.generateToken(userDetails))
                .thenReturn(token);

        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signIn(signInRequest);

        assertEquals(token, jwtAuthenticationResponse.getToken());

        verify(authenticationManager, times(1))
                .authenticate(any(Authentication.class));
    }
}
