package ru.orlov.jwtauthapp.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.orlov.jwtauthapp.exception.UsernameAlreadyExistsException;
import ru.orlov.jwtauthapp.model.Role;
import ru.orlov.jwtauthapp.model.User;
import ru.orlov.jwtauthapp.security.model.UserSecDetails;
import ru.orlov.jwtauthapp.security.model.dto.JwtAuthenticationResponse;
import ru.orlov.jwtauthapp.security.model.dto.SignInRequest;
import ru.orlov.jwtauthapp.security.model.dto.SignUpRequest;
import ru.orlov.jwtauthapp.service.UserService;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final UserSecDetailsService userSecDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        String username = request.getUsername();
        if (userService.existsUserByUsername(username)) {
            throw new UsernameAlreadyExistsException("Username: " + username + " уже существует");
        }
        User user = buildUser(request);
        userService.saveUser(user);
        String jwt = jwtService.generateToken(new UserSecDetails(user));
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(getAuthToken(request));
        UserDetails userDetails = userSecDetailsService.loadUserByUsername(request.getUsername());
        String jwt = jwtService.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwt);
    }

    private UsernamePasswordAuthenticationToken getAuthToken(SignInRequest request) {
        return new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    }

    private User buildUser(SignUpRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
    }
}
