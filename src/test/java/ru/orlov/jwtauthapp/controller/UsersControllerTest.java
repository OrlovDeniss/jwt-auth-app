package ru.orlov.jwtauthapp.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.orlov.jwtauthapp.security.config.SecurityConfig;
import ru.orlov.jwtauthapp.security.service.AuthenticationService;
import ru.orlov.jwtauthapp.security.service.JwtService;
import ru.orlov.jwtauthapp.security.service.UserSecDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResourceController.class)
@Import(SecurityConfig.class)
class UsersControllerTest {

    private static final String REQUEST_MAPPING = "/resources";
    private static final String PROTECTED_REQUEST_MAPPING = REQUEST_MAPPING + "/protected";
    private static final String PRIVATE_REQUEST_MAPPING = REQUEST_MAPPING + "/private";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserSecDetailsService userSecDetailsService;
    @MockBean
    private AuthenticationService authenticationService;

    @SneakyThrows
    @Test
    @WithAnonymousUser
    void anonymousUserGetProtectedResourcesTest() {
        mvc.perform(get(PROTECTED_REQUEST_MAPPING)).andExpect(status().isForbidden());
    }

    @SneakyThrows
    @Test
    @WithMockUser(roles = "USER")
    void userGetProtectedResourcesTest() {
        mvc.perform(get(PROTECTED_REQUEST_MAPPING)).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminGetProtectedResourcesTest() {
        mvc.perform(get(PROTECTED_REQUEST_MAPPING)).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    @WithAnonymousUser
    void anonymousUserGetPrivateResourcesTest() {
        mvc.perform(get(PRIVATE_REQUEST_MAPPING)).andExpect(status().isForbidden());
    }

    @SneakyThrows
    @Test
    @WithMockUser(roles = "USER")
    void userGetProtectedPrivateResourcesTest() {
        mvc.perform(get(PRIVATE_REQUEST_MAPPING)).andExpect(status().isForbidden());
    }

    @SneakyThrows
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminGetProtectedPrivateResourcesTest() {
        mvc.perform(get(PRIVATE_REQUEST_MAPPING)).andExpect(status().isOk());
    }
}
