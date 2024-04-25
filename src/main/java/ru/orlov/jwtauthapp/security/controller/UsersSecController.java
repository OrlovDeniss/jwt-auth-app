package ru.orlov.jwtauthapp.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.orlov.jwtauthapp.security.model.dto.JwtAuthenticationResponse;
import ru.orlov.jwtauthapp.security.model.dto.SignInRequest;
import ru.orlov.jwtauthapp.security.model.dto.SignUpRequest;
import ru.orlov.jwtauthapp.security.service.AuthenticationService;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class UsersSecController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @ApiResponse(responseCode = "200", description = "Получение существующей задачи по id",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationResponse.class))})
    @PostMapping("signup")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @ApiResponse(responseCode = "200", description = "Получение существующей задачи по id",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationResponse.class))})
    @PostMapping("signin")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
