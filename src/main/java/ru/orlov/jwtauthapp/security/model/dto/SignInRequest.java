package ru.orlov.jwtauthapp.security.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Size(max = 50)
    @NotBlank
    private String username;

    @Size(max = 255)
    @NotBlank
    private String password;
}