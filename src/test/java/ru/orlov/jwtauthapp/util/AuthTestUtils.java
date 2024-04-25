package ru.orlov.jwtauthapp.util;

import lombok.experimental.UtilityClass;
import org.jeasy.random.EasyRandom;
import ru.orlov.jwtauthapp.security.model.dto.JwtAuthenticationResponse;
import ru.orlov.jwtauthapp.security.model.dto.SignInRequest;
import ru.orlov.jwtauthapp.security.model.dto.SignUpRequest;

@UtilityClass
public class AuthTestUtils {

    private final EasyRandom easyRandom = new EasyRandom();

    public String createToken() {
        return easyRandom.nextObject(String.class);
    }

    public SignUpRequest createSignUpRequest() {
        return easyRandom.nextObject(SignUpRequest.class);
    }

    public SignUpRequest createSignUpRequest(String email) {
        SignUpRequest signUpRequest = createSignUpRequest();
        signUpRequest.setEmail(email);
        return signUpRequest;
    }

    public SignInRequest createSignInRequest() {
        return easyRandom.nextObject(SignInRequest.class);
    }

    public JwtAuthenticationResponse createJwtAuthenticationResponse() {
        return easyRandom.nextObject(JwtAuthenticationResponse.class);
    }
}
