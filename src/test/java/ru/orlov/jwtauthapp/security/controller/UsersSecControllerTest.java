package ru.orlov.jwtauthapp.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.orlov.jwtauthapp.security.config.SecurityConfig;
import ru.orlov.jwtauthapp.security.model.dto.JwtAuthenticationResponse;
import ru.orlov.jwtauthapp.security.model.dto.SignInRequest;
import ru.orlov.jwtauthapp.security.model.dto.SignUpRequest;
import ru.orlov.jwtauthapp.security.service.AuthenticationService;
import ru.orlov.jwtauthapp.security.service.JwtService;
import ru.orlov.jwtauthapp.security.service.UserSecDetailsService;
import ru.orlov.jwtauthapp.util.AuthTestUtils;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersSecController.class)
@Import(SecurityConfig.class)
class UsersSecControllerTest {

    private static final String REQUEST_MAPPING = "/users";
    private static final String SIGNUP_MAPPING = REQUEST_MAPPING + "/signup";
    private static final String SIGNIN_MAPPING = REQUEST_MAPPING +"/signin";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserSecDetailsService userSecDetailsService;
    @MockBean
    private AuthenticationService authenticationService;

    @SneakyThrows
    @Test
    void signUpTest() {
        SignUpRequest signUpRequest = AuthTestUtils.createSignUpRequest("user@users.com");
        JwtAuthenticationResponse jwtResponse = AuthTestUtils.createJwtAuthenticationResponse();

        when(authenticationService.signUp(signUpRequest))
                .thenReturn(jwtResponse);

        mvc.perform(post(SIGNUP_MAPPING)
                        .content(objectMapper.writeValueAsString(signUpRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(jwtResponse.getToken())));

        verify(authenticationService, times(1))
                .signUp(any(SignUpRequest.class));
    }

    @SneakyThrows
    @Test
    void signInTest() {
        SignInRequest signInRequest = AuthTestUtils.createSignInRequest();
        JwtAuthenticationResponse jwtResponse = AuthTestUtils.createJwtAuthenticationResponse();

        when(authenticationService.signIn(signInRequest))
                .thenReturn(jwtResponse);

        mvc.perform(post(SIGNIN_MAPPING)
                        .content(objectMapper.writeValueAsString(signInRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(jwtResponse.getToken())));

        verify(authenticationService, times(1))
                .signIn(any(SignInRequest.class));
    }
}
